package team7.simple.domain.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class QuestionUpdateParam {
    @NotNull
    private Long questionId;
    @NotNull
    private String title;
    @NotNull
    private String content;
    @NotNull
    private Integer timeline;
    @NotNull
    private LocalDateTime createdTime;
}
