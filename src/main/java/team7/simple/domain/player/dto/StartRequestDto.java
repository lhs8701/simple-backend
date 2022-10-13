package team7.simple.domain.player.dto;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StartRequestDto {
    Long userId;
    Long courseId;
    Long unitId;
    boolean forced;
}
