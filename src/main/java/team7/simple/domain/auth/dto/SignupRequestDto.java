package team7.simple.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import team7.simple.domain.user.entity.User;

import java.util.Collections;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {
    private String account;
    private String password;

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .id(UUID.randomUUID().toString())
                .account(account)
                .password(passwordEncoder.encode(password))
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }
}