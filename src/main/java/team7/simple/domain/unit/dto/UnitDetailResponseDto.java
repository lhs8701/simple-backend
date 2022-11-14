package team7.simple.domain.unit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team7.simple.domain.question.dto.QuestionThumbnailResponseDto;
import team7.simple.domain.unit.entity.Unit;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnitDetailResponseDto {
    private Long unitId;
    private String title;

    private String fileUrl;

    private List<QuestionThumbnailResponseDto> questionList;


    public UnitDetailResponseDto(Unit unit) {
        this.unitId = unit.getUnitId();
        this.title = unit.getTitle();
        this.fileUrl = unit.getVideo().getHlsFileUrl();
        this.questionList = unit.getQuestionList().stream().map(QuestionThumbnailResponseDto::new).collect(Collectors.toList());
    }

}
