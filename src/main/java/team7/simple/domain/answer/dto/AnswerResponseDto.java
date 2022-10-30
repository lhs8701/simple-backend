package team7.simple.domain.answer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team7.simple.domain.answer.entity.Answer;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerResponseDto {
    private Long answerId;
    private String title;
    private String content;

    public AnswerResponseDto(Answer answer) {
        this.answerId = answer.getAnswerId();
        this.title = answer.getTitle();
        this.content = answer.getContent();
    }
}
