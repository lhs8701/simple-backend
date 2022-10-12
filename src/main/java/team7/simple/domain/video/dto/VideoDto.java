package team7.simple.domain.video.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team7.simple.domain.video.entity.Video;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VideoDto {
    private String fileName;
    private String fileOriName;
    private String fileUrl;

    public Video toEntity() {
        return Video.builder()
                .fileName(this.fileName)
                .fileOriName(this.fileOriName)
                .fileUrl(this.fileUrl)
                .build();
    }
}
