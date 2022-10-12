package team7.simple.infra.hls.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import team7.simple.domain.video.dto.VideoDto;
import team7.simple.domain.video.entity.Video;
import team7.simple.domain.video.repository.VideoJpaRepository;
import team7.simple.global.error.advice.exception.CFileNotFoundException;
import team7.simple.infra.hls.dto.HlsRequestDto;

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
            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
            executor.createJob(builder).run();
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        return outputPath.substring(ROOT_PATH.length());
    }

    public String getHlsFileUrl(Video video) {
        /* 파일 존재하는지 확인하는 로직 추가 */
        String url = ROOT_PATH + video.getHlsFileUrl();

        File file = new File(url);
        if (!file.exists()) {
            throw new CFileNotFoundException();
        }

        return video.getHlsFileUrl();
    }
}
