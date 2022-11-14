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
public class UnitThumbnailResponseDto {
    private Long unitId;
    private String title;

    public UnitThumbnailResponseDto(Unit unit) {
        this.unitId = unit.getUnitId();
        this.title = unit.getTitle();
    }

}
