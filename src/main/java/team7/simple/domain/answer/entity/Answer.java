package team7.simple.domain.answer.entity;

import lombok.*;
import team7.simple.domain.question.entity.Question;
import team7.simple.domain.user.entity.User;
import team7.simple.global.common.jpa.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Answer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @ManyToOne
    @JoinColumn
    private Question question;

    @ManyToOne
    @JoinColumn
    private User user;

    public void update(String content){
        this.content = content;
    }
}
