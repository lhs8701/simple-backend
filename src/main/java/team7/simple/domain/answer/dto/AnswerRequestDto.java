package team7.simple.domain.answer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team7.simple.domain.answer.entity.Answer;
import team7.simple.domain.question.entity.Question;
import team7.simple.domain.user.entity.User;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class AnswerRequestDto {
    @NotNull
    private String content;

    public Answer toEntity(Question question, User user) {
        return Answer.builder()
                .content(this.content)
                .question(question)
                .user(user)
                .build();
    }
}
