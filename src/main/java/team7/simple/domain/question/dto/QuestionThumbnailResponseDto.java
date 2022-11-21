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

    private int timeline;

    public QuestionThumbnailResponseDto(Question question) {
        this.questionId = question.getId();
        this.title = question.getTitle();
        this.replyCount = question.getAnswerList().size();
        this.timeline = question.getTimeline();
    }
}
