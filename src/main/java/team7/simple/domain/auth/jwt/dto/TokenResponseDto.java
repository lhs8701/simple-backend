package team7.simple.domain.auth.jwt.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponseDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
