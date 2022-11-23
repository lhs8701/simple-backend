package team7.simple.domain.file.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import team7.simple.domain.file.dto.VideoDto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VideoService {

    @Value("${server.root-path}")
    private String ROOT_PATH;

    @Value("${server.media-path}")
    private String MEDIA_PATH;

    public VideoDto uploadVideo(MultipartFile file, Long courseId) {
        String fileOriName = Optional.ofNullable(file.getOriginalFilename()).orElse("video");
        String fileName = makeFileName(fileOriName);
        String fileUrl = makeFileUrl(fileName, courseId);

        Path savePath = Paths.get(fileUrl);

        try {
            file.transferTo(savePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new VideoDto(fileName, fileOriName, fileUrl);
    }

    public String makeFileName(String fileOriName) {
        int idx = fileOriName.lastIndexOf(".");
        String ext = fileOriName.substring(idx);
        String uuid = UUID.randomUUID().toString();
        return uuid + ext;
    }

    public String makeFileUrl(String fileName, Long courseId) {
        String onlyFileName = fileName.substring(0, fileName.lastIndexOf("."));
        String directoryPath = ROOT_PATH + MEDIA_PATH + "/" + courseId.toString() + "/" + onlyFileName;

        File uploadPathFolder = new File(directoryPath);
        if (!uploadPathFolder.exists()) {
            uploadPathFolder.mkdirs();
        }

        return directoryPath + "/" + fileName;
    }
}
