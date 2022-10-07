package team7.simple.domain.unit.entity;

import lombok.*;

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

    @Builder
    public Unit(Long unitId, String title, String mediaUrl) {
        this.unitId = unitId;
        this.title = title;
        this.mediaUrl = mediaUrl;

    }
}
