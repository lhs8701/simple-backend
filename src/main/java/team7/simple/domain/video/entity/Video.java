package team7.simple.domain.video.entity;

import lombok.*;
import team7.simple.domain.unit.entity.Unit;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long videoId;

    @OneToOne(mappedBy = "video", fetch = FetchType.LAZY)
    private Unit unit;

    private String fileName;
    private String fileOriName;
    private String fileUrl;

    /*임시*/
    public Video(String fileName, String fileOriName, String fileUrl){
        this.fileName = fileName;
        this.fileOriName = fileOriName;
        this.fileUrl = fileUrl;
    }
}
