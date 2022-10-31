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
public class QuestionResponseDto {
    private Long questionId;
    private String title;
    private String content;
    private Integer replyCount;
    private Integer timeline;
    private LocalDateTime createdTime;
    private List<Answer> answerList;

    public QuestionResponseDto(Question question) {
        this.questionId = question.getQuestionId();
        this.title = question.getTitle();
        this.content = question.getContent();
        //this.replyCount = question.getReplyCount();
        this.timeline = question.getTimeline();
        this.createdTime = question.getCreatedTime();
        this.answerList = question.getAnswerList();
        this.replyCount = this.answerList.size();
    }
}
