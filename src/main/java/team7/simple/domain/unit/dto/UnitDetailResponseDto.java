package team7.simple.domain.unit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team7.simple.domain.rating.dto.RatingDetailResponseDto;
import team7.simple.domain.unit.entity.Unit;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnitDetailResponseDto {
    private Long unitId;
    private String title;
    private String description;
    private String objective;

    private double averageScore;

    private List<RatingDetailResponseDto> ratingList;

    public UnitDetailResponseDto(Unit unit, double averageScore, List<RatingDetailResponseDto> ratingList) {
        this.unitId = unit.getId();
        this.title = unit.getTitle();
        this.description = unit.getDescription();
        this.objective = unit.getObjective();
        this.averageScore = averageScore;
        this.ratingList = ratingList;
    }
}
