package team7.simple.domain.question.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team7.simple.domain.answer.entity.Answer;
import team7.simple.domain.question.entity.Question;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDetailResponseDto {
    private Long questionId;
    private String title;
    private String content;
    private Integer replyCount;
    private Integer timeline;
    private LocalDateTime createdTime;
    private List<Answer> answerList;

    public QuestionDetailResponseDto(Question question) {
        this.questionId = question.getId();
        this.title = question.getTitle();
        this.content = question.getContent();
        this.timeline = question.getTimeline();
        this.createdTime = question.getCreatedTime();
        this.answerList = question.getAnswerList();
        this.replyCount = this.answerList.size();
    }
}
