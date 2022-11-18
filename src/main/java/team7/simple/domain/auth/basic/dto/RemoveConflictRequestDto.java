package team7.simple.domain.auth.basic.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RemoveConflictRequestDto {
    String accessToken;
    boolean keepGoing;
}
