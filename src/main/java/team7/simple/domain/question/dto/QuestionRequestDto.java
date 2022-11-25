package team7.simple.domain.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team7.simple.domain.question.entity.Question;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.user.entity.User;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class QuestionRequestDto {
    @NotNull
    private String title;
    @NotNull
    private String content;
    @NotNull
    private int timeline;

    public Question toEntity(Unit unit, User user) {
        return Question.builder()
                .title(this.title)
                .content(this.content)
                .timeline(this.timeline)
                .unit(unit)
                .user(user)
                .build();
    }
}
