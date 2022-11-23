package team7.simple.domain.file.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team7.simple.domain.file.entity.Video;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VideoDto {
    private String fileName;
    private String fileOriName;
    private String fileUrl;

    public Video toEntity(String hlsFileUrl) {
        return Video.builder()
                .fileName(this.fileName)
                .fileOriName(this.fileOriName)
                .fileUrl(this.fileUrl)
                .hlsFileUrl(hlsFileUrl)
                .build();
    }
}
