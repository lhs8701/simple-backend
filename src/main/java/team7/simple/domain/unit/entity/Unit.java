package team7.simple.domain.unit.entity;

import lombok.*;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.question.entity.Question;
import team7.simple.domain.file.entity.Video;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

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
