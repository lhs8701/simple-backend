package team7.simple.domain.unit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team7.simple.domain.question.dto.QuestionResponseDto;
import team7.simple.domain.question.entity.Question;
import team7.simple.domain.unit.entity.Unit;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnitResponseDto {
    private Long unitId;
    private String title;

    private String fileUrl;

    private List<Question> questionList;

    public UnitResponseDto(Unit unit) {
        this.unitId = unit.getUnitId();
        this.title = unit.getTitle();
        this.questionList = unit.getQuestionList();
    }

}
