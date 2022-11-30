package team7.simple.infra.hls.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import team7.simple.domain.file.dto.VideoDto;
import team7.simple.domain.file.entity.Video;
import team7.simple.domain.file.repository.VideoJpaRepository;
import team7.simple.domain.file.error.exception.CFileNotFoundException;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class HlsService {

    @Value("${hls.ffmpeg}")
    private String FFMPEG_PATH;

    @Value("${hls.ffprobe}")
    private String FFPROBE_PATH;

    @Value("${server.root-path}")
    private String ROOT_PATH;

    private final VideoJpaRepository videoJpaRepository;

    private final Environment env;


    /**
     * 영상을 ffmpeg를 이용해 .m3u8확장자로 변환한 뒤, 원격 서버에 전송합니다.
     * @param videoDto
     * @return
     */
    public String convertToM3u8(VideoDto videoDto) {
        String fileName = videoDto.getFileName();
        String onlyFileName = fileName.substring(0, fileName.lastIndexOf("."));
        String fileUrl = videoDto.getFileUrl();
        String directoryPath = fileUrl.substring(0, fileUrl.lastIndexOf("/"));

        String inputPath = fileUrl; // .mp4 ( 경로 : /upload_dir_path/name.mp4
        String outputPath = directoryPath + "/" + onlyFileName + "-m3u8" + "/" + onlyFileName + ".m3u8"; // .m3u8

        log.info("fileName : {}", fileName);
        log.info("onlyFileName : {}", onlyFileName);
        log.info("fileUrl : {}", fileUrl);
        log.info("fileDir : {}", directoryPath);
        log.info("inputPath : {}", inputPath);
        log.info("outputPath : {}", outputPath);

        File tsPath = new File(directoryPath + "/" + onlyFileName + "-m3u8");
        if (!tsPath.exists()) {
            tsPath.mkdir();
        }

        try {
            FFmpeg ffmpeg = new FFmpeg(FFMPEG_PATH);
            FFprobe ffprobe = new FFprobe(FFPROBE_PATH);
            FFmpegProbeResult probeResult = ffprobe.probe(inputPath);

            // TS 파일 생성
            FFmpegBuilder builder = new FFmpegBuilder()
                    //.overrideOutputFiles(true) // 오버라이드 여부
                    .setInput(inputPath) // 동영상파일 (inputPath)
                    .addOutput(outputPath) // 저장 경로 : /upload_dir_path/name/name.m3u8
                    .addExtraArgs("-profile:v", "baseline") //
                    .addExtraArgs("-level", "3.0") //
                    .addExtraArgs("-start_number", "0") //
                    .addExtraArgs("-hls_time", "1") //
                    .addExtraArgs("-hls_list_size", "0") //
                    .addExtraArgs("-f", "hls") //
                    .done();
            log.info("start execute");
            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
            executor.createJob(builder).run();
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        log.info("finish execute");
        return outputPath.substring(ROOT_PATH.length());
    }


    /**
     * 원격 서버에서 해당 영상의 .m3u8확장자 파일 경로를 반환합니다.
     * @param video 영상
     * @return .m3u8확장자 파일 경로
     */
    public String getHlsFileUrl(Video video) {
        String hlsFileUrl = video.getHlsFileUrl();

        /* 파일 존재하는지 확인하는 로직 추가 */
        String url = ROOT_PATH + hlsFileUrl;

        File file = new File(url);
        if (!file.exists()) {
            throw new CFileNotFoundException();
        }

        return hlsFileUrl;
    }
}
