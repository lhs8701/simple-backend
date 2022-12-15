package team7.simple.domain.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResolveConflictRequestDto {
    String accessToken;
    boolean keepGoing;
}
