package team7.simple.domain.unit.entity;

import lombok.*;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.question.entity.Question;
import team7.simple.domain.file.entity.Video;
import team7.simple.domain.rating.entity.Rating;
import team7.simple.domain.record.entity.Record;
import team7.simple.global.common.jpa.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
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
    private List<Question> questionList = new ArrayList<Question>();

    @OneToMany(mappedBy = "unit", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Rating> ratingList = new ArrayList<>();

    @OneToMany(mappedBy = "unit", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Record> recordList = new ArrayList<>();

    public void update(String title, String description, String objective){
        this.title = title;
        this.description = description;
        this.objective = objective;
    }
}
