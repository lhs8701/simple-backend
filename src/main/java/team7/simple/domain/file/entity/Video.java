package team7.simple.domain.file.entity;

import lombok.*;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.global.common.jpa.BaseTimeEntity;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Builder
public class Video extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String fileOriName;
    private String fileUrl;

    private String hlsFileUrl;

    @OneToOne(mappedBy = "video", fetch = FetchType.LAZY)
    private Unit unit;

    /*임시*/
    public Video(String fileName, String fileOriName, String fileUrl){
        this.fileName = fileName;
        this.fileOriName = fileOriName;
        this.fileUrl = fileUrl;
    }
}
