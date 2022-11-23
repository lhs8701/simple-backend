package team7.simple.domain.unit.dto;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UnitPlayRequestDto {
    private Long currentUnitId;
    private double recordTime;

    @ApiParam(defaultValue = "false")
    private boolean complete;
}
