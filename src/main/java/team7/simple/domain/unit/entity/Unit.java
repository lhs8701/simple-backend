package team7.simple.domain.unit.entity;

import lombok.*;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.question.entity.Question;
import team7.simple.domain.file.entity.Video;
import team7.simple.global.common.jpa.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Unit extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String objective;

    @ManyToOne
    @JoinColumn
    private Course course;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Video video;

    @OneToMany(mappedBy = "unit", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Question> questionList = new ArrayList<Question>();
}
