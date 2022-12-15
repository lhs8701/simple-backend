package team7.simple.domain.auth.jwt.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;


@Getter
@NoArgsConstructor
@RedisHash("refreshToken")
public class RefreshToken {

    @Id
    private String userId;

    private String refreshToken;

    @TimeToLive
    private Long expiration;

    public RefreshToken(String userId, String refreshToken) {
        this.userId = userId;
        this.refreshToken = refreshToken;
        this.expiration = JwtExpiration.REFRESH_TOKEN_EXPIRATION_TIME.getValue() / 1000;
    }
}