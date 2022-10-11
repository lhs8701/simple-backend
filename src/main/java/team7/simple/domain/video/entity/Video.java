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
}
