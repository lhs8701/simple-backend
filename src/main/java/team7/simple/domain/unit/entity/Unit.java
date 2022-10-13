package team7.simple.domain.unit.entity;

import lombok.*;
import org.hibernate.mapping.Join;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.video.entity.Video;

import javax.persistence.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long unitId;
    private String title;

    @ManyToOne
    @JoinColumn
    private Course course;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Video video;
}
