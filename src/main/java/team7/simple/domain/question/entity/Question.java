package team7.simple.domain.question.entity;

import lombok.*;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.unit.entity.Unit;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;
    private String title;
    private String content;
    //private Integer replyCount;
    private Integer timeline;
    private LocalDateTime createdTime;

    @ManyToOne
    @JoinColumn
    private Unit unit;

}
