package team7.simple.domain.auth.jwt.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

// 현재 플레이어를 실행중인 어세스 토큰
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@RedisHash("activeAccessToken")
public class ActiveAccessToken {
    @Id
    private String accessToken;

    @Indexed
    private String userId;

    @Indexed
    private int conflict;

    @Indexed
    private boolean valid;

    @TimeToLive
    private Long expiration;
}
