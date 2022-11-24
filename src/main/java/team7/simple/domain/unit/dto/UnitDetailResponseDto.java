package team7.simple.domain.unit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team7.simple.domain.unit.entity.Unit;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnitDetailResponseDto {
    private Long unitId;
    private String title;
    private String description;
    private String objective;

    public UnitDetailResponseDto(Unit unit) {
        this.unitId = unit.getId();
        this.title = unit.getTitle();
        this.description = unit.getDescription();
        this.objective = unit.getObjective();
    }
}
