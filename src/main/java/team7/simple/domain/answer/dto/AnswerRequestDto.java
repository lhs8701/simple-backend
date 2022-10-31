package team7.simple.domain.answer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team7.simple.domain.answer.entity.Answer;
import team7.simple.domain.question.entity.Question;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class AnswerRequestDto {
    @NotNull
    private String title;
    @NotNull
    private String content;

    public Answer toEntity(Question question) {
        return Answer.builder()
                .title(this.title)
                .content(this.content)
                .question(question)
                .build();
    }
}
