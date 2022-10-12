package team7.simple.domain.unit.dto;

import lombok.Getter;
import team7.simple.domain.unit.entity.Unit;

import java.util.List;

@Getter
public class UnitResponseDto {

    private Long unitId;
    private String title;

    public UnitResponseDto(Unit unit) {
        this.unitId = unit.getUnitId();
        this.title = unit.getTitle();

    }
}
