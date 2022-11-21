package team7.simple.domain.question.entity;

import lombok.*;
import team7.simple.domain.answer.entity.Answer;
import team7.simple.domain.unit.entity.Unit;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    //private Integer replyCount;
    private Integer timeline;
    private LocalDateTime createdTime;

    @ManyToOne
    @JoinColumn
    private Unit unit;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Answer> answerList = new ArrayList<Answer>();

}
