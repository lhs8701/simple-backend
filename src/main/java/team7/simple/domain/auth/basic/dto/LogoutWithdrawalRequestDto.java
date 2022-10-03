package team7.simple.domain.auth.basic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class LogoutWithdrawalRequestDto {
    String socialAccessToken;
}
