package team7.simple.domain.answer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AnswerUpdateParam {
    @NotNull
    private Long answerId;
    @NotNull
    private String title;
    @NotNull
    private String content;

}
