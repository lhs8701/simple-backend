package team7.simple.domain.answer.entity;

import lombok.*;
import team7.simple.domain.question.entity.Question;

import javax.persistence.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;
    private String title;
    private String content;

    @ManyToOne
    @JoinColumn
    private Question question;
}
