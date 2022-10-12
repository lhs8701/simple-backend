package team7.simple.domain.auth.jwt.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@NoArgsConstructor
@RedisHash("activeAccessToken")
public class ActiveAccessToken {
    @Id
    private String accessToken;

    @Indexed //redis repository에서 인자로 사용하는 필드에 붙여줘야 함
    private Long userId;

    @TimeToLive
    private Long expiration;

    public ActiveAccessToken(String accessToken, Long userId) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.expiration = JwtExpiration.ACCESS_TOKEN_EXPIRATION_TIME.getValue() / 1000;
    }
}
