package team7.simple.domain.unit.entity;

import lombok.*;
import team7.simple.domain.video.entity.Video;

import javax.persistence.*;

@Getter
@Setter
@Builder
@Entity
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long unitId;
    private String title;
    private String mediaUrl;
    @OneToOne(cascade = CascadeType.ALL)
    private Video video;

    @Builder
    public Unit(Long unitId, String title, String mediaUrl, Video video) {
        this.unitId = unitId;
        this.title = title;
        this.mediaUrl = mediaUrl;
        this.video = video;

    }
}
