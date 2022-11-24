package team7.simple.domain.unit.dto;

import lombok.AllArgsConstructor;
import team7.simple.domain.unit.entity.Unit;

public class UnitHistoryResponseDto {
    private Long unitId;
    private String title;
    private boolean completed;
    public UnitHistoryResponseDto(Unit unit, boolean completed){
        this.unitId = unit.getId();
        this.title = unit.getTitle();
        this.completed = completed;
    }
}

