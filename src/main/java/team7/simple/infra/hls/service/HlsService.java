package team7.simple.infra.hls.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class HlsService {

    private final Environment env;

    @Value("${hls.media.upload_dir}")
    private String UPLOAD_DIR;

    public void convertToM3u8(String fileName){
        final String FFMPEG = env.getProperty("hls.ffmpeg");
        final String FFPROBE = env.getProperty("hls.ffprobe");
        final String onlyFileName = fileName.substring(0, fileName.lastIndexOf(".")); //path/이름
        String inputPath = UPLOAD_DIR + "/" + fileName; // .mp4 ( 경로 : /upload_dir_path/name.mp4
        String outputPath = UPLOAD_DIR + "/" + onlyFileName; // .m3u8
        File tsPath = new File(outputPath);
        if (!tsPath.exists()) {
            tsPath.mkdir();
        }

        try {
            FFmpeg ffmpeg = new FFmpeg(FFMPEG);
            FFprobe ffprobe = new FFprobe(FFPROBE);
            FFmpegProbeResult probeResult = ffprobe.probe(inputPath);

            // TS 파일 생성
            FFmpegBuilder builder = new FFmpegBuilder()
                    //.overrideOutputFiles(true) // 오버라이드 여부
                    .setInput(inputPath) // 동영상파일 (inputPath)
                    .addOutput(outputPath + "/" + onlyFileName + ".m3u8") // 저장 경로 : /upload_dir_path/name/name.m3u8
                    .addExtraArgs("-profile:v", "baseline") //
                    .addExtraArgs("-level", "3.0") //
                    .addExtraArgs("-start_number", "0") //
                    .addExtraArgs("-hls_time", "10") //
                    .addExtraArgs("-hls_list_size", "0") //
                    .addExtraArgs("-f", "hls") //
                    .done();
            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
            executor.createJob(builder).run();

            // 이미지 파일 생성
            FFmpegBuilder builderThumbNail = new FFmpegBuilder()
                    .overrideOutputFiles(true) // 오버라이드 여부
                    .setInput(inputPath) // 동영상파일
                    .addExtraArgs("-ss", "00:00:03") // 썸네일 추출 시작점
                    .addOutput(UPLOAD_DIR + "/" + onlyFileName + ".png") // 썸네일 경로 : /upload_dir_path/name.png
                    .setFrames(1) // 프레임 수
                    .done();
            FFmpegExecutor executorThumbNail = new FFmpegExecutor(ffmpeg, ffprobe);
            executorThumbNail.createJob(builderThumbNail).run();
        }catch(IOException e){
            log.info(e.getMessage());
        }
    }

    public String getM3u8Url(String fileName) {
        return UPLOAD_DIR + "/" + fileName + "/" + fileName + ".m3u8";
    }
}
