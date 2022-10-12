package team7.simple.domain.video.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import team7.simple.domain.video.dto.VideoDto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VideoService {

    private String UploadPath;

    private String root;

    public VideoDto uploadVideo(MultipartFile file, Long courseId) {
        String fileOriName = Optional.ofNullable(file.getOriginalFilename()).orElse("video");
        String fileName = makeFileName(fileOriName);
        String fileUrl = makeFileUrl(fileName, UploadPath, courseId);

        String transferUrl = root + fileUrl;
        Path savePath = Paths.get(transferUrl);

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

    public String makeFileUrl(String fileName, String uploadPath, Long courseId) {
        if (root == null)
            root = "";

        String str_folder = root + uploadPath + courseId.toString();
        String str_url = uploadPath + courseId.toString() + "/" + fileName;
        String folderPath = str_folder.replace("/", File.separator);

        File uploadPathFolder = new File(folderPath);
        if (!uploadPathFolder.exists()) {
            uploadPathFolder.mkdirs();
        }

        return str_url.replace("/", File.separator);
    }
}
