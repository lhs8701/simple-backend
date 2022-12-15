package team7.simple.domain.auth.jwt.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;


@Getter
@NoArgsConstructor
@RedisHash("logoutAccessToken")
public class LogoutAccessToken {

    @Id
    private String accessToken;

    @TimeToLive
    private Long expiration;

    public LogoutAccessToken(String accessToken, Long remainingMilliSeconds) {
        this.accessToken = accessToken;
        this.expiration = remainingMilliSeconds / 1000;
    }
}