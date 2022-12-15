package team7.simple.domain.question.entity;

import lombok.*;
import team7.simple.domain.answer.entity.Answer;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.user.entity.User;
import team7.simple.global.common.jpa.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Question extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private int timeline;

    @ManyToOne
    @JoinColumn
    private Unit unit;

    @ManyToOne
    @JoinColumn
    private User user;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Answer> answerList = new ArrayList<>();

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
