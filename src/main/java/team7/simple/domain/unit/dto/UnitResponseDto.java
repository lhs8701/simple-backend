package team7.simple.domain.unit.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import team7.simple.domain.rating.dto.RatingDetailResponseDto;
import team7.simple.domain.unit.entity.Unit;

import java.util.List;

@NoArgsConstructor
@Getter
public class UnitResponseDto {
    private Long unitId;
    private String title;
    private String description;
    private String objective;


    public UnitResponseDto(Unit unit) {
        this.unitId = unit.getId();
        this.title = unit.getTitle();
        this.description = unit.getDescription();
        this.objective = unit.getObjective();
    }
}

