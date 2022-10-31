package team7.simple.domain.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team7.simple.domain.question.entity.Question;
import team7.simple.domain.unit.entity.Unit;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class QuestionRequestDto {
    @NotNull
    private String title;
    @NotNull
    private String content;
    @NotNull
    private Integer timeline;

    public Question toEntity(Unit unit) {
        return Question.builder()
                .title(this.title)
                .content(this.content)
                .timeline(this.timeline)
                .unit(unit)
                .build();
    }
}
