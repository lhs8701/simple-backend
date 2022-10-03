package team7.simple.infra.hls.controller;

import io.swagger.models.Model;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HlsController {
    private final Environment env;

    @Value("${hls.media.upload_dir}")
    private String UPLOAD_DIR;


    /* 서버에 있는 .mp4를 바탕으로 .m3u8 생성 */
    @GetMapping("/hls-make/{fileName}")
    @ResponseBody
    @PreAuthorize("permitAll()")
    public void videoHlsMake(@PathVariable String fileName) throws IOException {
        final String FFMPEG = env.getProperty("hls.ffmpeg");
        final String FFPROBE = env.getProperty("hls.ffprobe");
        final String onlyFileName = fileName.substring(0, fileName.lastIndexOf(".")); //path/이름
        String inputPath = UPLOAD_DIR + "/" + fileName; //upload_dir/file.mp4
        String outputPath = UPLOAD_DIR + "/" + onlyFileName; //upload_dir/name
        File tsPath = new File(outputPath);
        if (!tsPath.exists()) {
            tsPath.mkdir();
        }

        FFmpeg ffmpeg = new FFmpeg(FFMPEG);
        FFprobe ffprobe = new FFprobe(FFPROBE);

        FFmpegProbeResult probeResult = ffprobe.probe(inputPath);

        log.debug("========== VideoFileUtils.getMediaInfo() ==========");
        log.debug("filename : {}", probeResult.getFormat().filename);
        log.debug("format_name : {}", probeResult.getFormat().format_name);
        log.debug("format_long_name : {}", probeResult.getFormat().format_long_name);
        log.debug("tags : {}", probeResult.getFormat().tags.toString());
        log.debug("duration : {} second", probeResult.getFormat().duration);
        log.debug("size : {} byte", probeResult.getFormat().size);
        log.debug("width : {} px", probeResult.getStreams().get(0).width);
        log.debug("height : {} px", probeResult.getStreams().get(0).height);
        log.debug("===================================================");

        // TS 파일 생성
        FFmpegBuilder builder = new FFmpegBuilder()
                //.overrideOutputFiles(true) // 오버라이드 여부
                .setInput(inputPath) // 동영상파일 (inputPath)
                .addOutput(outputPath + "/" + onlyFileName + ".m3u8") // /upload_dir/name/name.m3u8
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
                .addOutput(UPLOAD_DIR + "/" + onlyFileName + ".png") // 썸네일 경로 /upload_dir/name.png
                .setFrames(1) // 프레임 수
                .done();
        FFmpegExecutor executorThumbNail = new FFmpegExecutor(ffmpeg, ffprobe);
        executorThumbNail.createJob(builderThumbNail).run();
    }

    /* m3u8 파일 재생 */
    @PreAuthorize("permitAll()")
    @GetMapping("/hls/{fileName}/{fileName}.m3u8")
    public ResponseEntity<Resource> videoHlsM3U8(@PathVariable String fileName) {
        String fileFullPath = UPLOAD_DIR + "/" + fileName + "/" + fileName + ".m3u8";
        Resource resource = new FileSystemResource(fileFullPath);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName + ".m3u8");
        headers.setContentType(MediaType.parseMediaType("application/vnd.apple.mpegurl"));
        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
    }

    /*ts 파일 재생*/
    @PreAuthorize("permitAll()")
    @GetMapping("/hls/{fileName}/{tsName}.ts")
    public ResponseEntity<Resource> videoHlsTs(@PathVariable String fileName, @PathVariable String tsName) {
        String fileFullPath = UPLOAD_DIR + "/" + fileName + "/" + tsName + ".ts";
        Resource resource = new FileSystemResource(fileFullPath);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + tsName + ".ts");
        headers.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE));
        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
    }
}
