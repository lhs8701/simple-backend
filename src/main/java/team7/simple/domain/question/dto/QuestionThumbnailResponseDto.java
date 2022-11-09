package team7.simple.domain.question.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import team7.simple.domain.question.entity.Question;

@NoArgsConstructor
@Getter
public class QuestionThumbnailResponseDto {
    private Long questionId;
    private String title;

    private int replyCount;

    public QuestionThumbnailResponseDto(Question question) {
        this.questionId = question.getQuestionId();
        this.title = question.getTitle();
        this.replyCount = question.getAnswerList().size();
    }
}
