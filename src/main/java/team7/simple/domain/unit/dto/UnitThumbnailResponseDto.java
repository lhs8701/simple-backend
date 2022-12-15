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
public class UnitThumbnailResponseDto {
    private Long unitId;
    private String title;
    private boolean completed;

    public UnitThumbnailResponseDto(Unit unit, boolean completed) {
        this.unitId = unit.getId();
        this.title = unit.getTitle();
        this.completed = completed;
    }

    public UnitThumbnailResponseDto(Unit unit) {
        this.unitId = unit.getId();
        this.title = unit.getTitle();
        this.completed = false;
    }

}
