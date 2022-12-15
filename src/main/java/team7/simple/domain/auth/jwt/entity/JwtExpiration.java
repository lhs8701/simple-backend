package team7.simple.domain.auth.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtExpiration {
    /* 정상 만료 */
//    ACCESS_TOKEN_EXPIRATION_TIME("JWT 만료 시간 / 60분", 1000L * 60 * 60),
//    REFRESH_TOKEN_EXPIRATION_TIME("Refresh 토큰 만료 시간 / 14일", 1000L * 60 * 60 * 24 * 14);

    /* 짧게 만료 */
//    ACCESS_TOKEN_EXPIRATION_TIME("JWT 만료 시간 / 30초", 1000L * 30),
//    REFRESH_TOKEN_EXPIRATION_TIME("Refresh 토큰 만료 시간 / 60초", 1000L * 60),

    /* 길게 만료 */
    ACCESS_TOKEN_EXPIRATION_TIME("JWT 만료 시간 / 1년", 1000L * 60 * 60 * 24 * 365),
    REFRESH_TOKEN_EXPIRATION_TIME("Refresh 토큰 만료 시간 / 1년", 1000L * 60 * 60 * 24 * 365);

    private final String description;
    private final Long value;
}