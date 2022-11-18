package team7.simple.domain.player.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExitRequestDto {
    String userId;
    Long unitId;
    double time;
}
