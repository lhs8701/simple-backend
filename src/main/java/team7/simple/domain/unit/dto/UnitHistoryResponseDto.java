package team7.simple.domain.unit.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import team7.simple.domain.unit.entity.Unit;

import javax.persistence.GeneratedValue;

@NoArgsConstructor
@Getter
public class UnitHistoryResponseDto {
    private Long unitId;
    private String title;
    private boolean completed;
    private double progress;

    public UnitHistoryResponseDto(Unit unit, boolean completed, double progress){
        this.unitId = unit.getId();
        this.title = unit.getTitle();
        this.completed = completed;
        this.progress = progress;
    }
}

