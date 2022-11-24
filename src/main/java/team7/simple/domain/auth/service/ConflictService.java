package team7.simple.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import team7.simple.domain.auth.dto.ResolveConflictRequestDto;
import team7.simple.domain.auth.error.exception.CWrongTypeTokenException;
import team7.simple.domain.auth.jwt.entity.ActiveAccessToken;
import team7.simple.domain.auth.jwt.repository.ActiveAccessTokenRedisRepository;
import team7.simple.domain.user.entity.User;
import team7.simple.domain.user.error.exception.CUserNotActiveException;
import team7.simple.domain.user.error.exception.CUserNotFoundException;
import team7.simple.global.common.constant.ActiveStatus;
import team7.simple.global.error.advice.exception.CWrongApproach;
import team7.simple.global.security.JwtProvider;

@Service
@RequiredArgsConstructor
public class ConflictService {
    private final ActiveAccessTokenRedisRepository activeAccessTokenRedisRepository;
    private final JwtProvider jwtProvider;

    /**
     * 해당 사용자의 계정으로 접속 중인 Active 토큰이 있는지 확인하고, 충돌 상태를 리턴한다.
     * 만약, 충돌이 발생할 경우, 기존 Active 토큰의 충돌 상태도 갱신한다.
     * @param user 사용자
     * @return 새로 접속하는 토큰의 충돌 상태
     */
    public int doesConflicted(User user) {
        ActiveAccessToken existActiveAccessToken = activeAccessTokenRedisRepository.findByUserId(user.getId()).orElse(null);
        if (existActiveAccessToken != null) {
            updateConflictStatus(existActiveAccessToken, ActiveStatus.PRE_CONFLICTED.ordinal());
            return ActiveStatus.POST_CONFLICTED.ordinal();
        }
        return ActiveStatus.NO_CONFLICT.ordinal();
    }

    /**
     * Active 토큰의 충돌 상태를 갱신한다.
     * Redis Repository에서 setter가 제대로 동작하지 않아서, 작성한 메소드이다.
     * @param token Active 토큰
     * @param conflictStatus 변경할 충돌 상태
     */
    public void updateConflictStatus(ActiveAccessToken token, int conflictStatus) {
        ActiveAccessToken newToken = ActiveAccessToken.builder()
                .accessToken(token.getAccessToken())
                .userId(token.getUserId())
                .conflict(conflictStatus)
                .valid(token.isValid())
                .expiration(token.getExpiration())
                .build();
        activeAccessTokenRedisRepository.delete(token);
        activeAccessTokenRedisRepository.save(newToken);
    }

    private void updateValid(ActiveAccessToken token, boolean valid) {
        ActiveAccessToken newToken = ActiveAccessToken.builder()
                .accessToken(token.getAccessToken())
                .userId(token.getUserId())
                .conflict(token.getConflict())
                .valid(valid)
                .expiration(token.getExpiration())
                .build();
        activeAccessTokenRedisRepository.delete(token);
        activeAccessTokenRedisRepository.save(newToken);
    }

    public ActiveAccessToken getLatestActiveToken(User user) {
        ActiveAccessToken activeAccessToken = activeAccessTokenRedisRepository
                .findByUserIdAndValid(user.getId(), false)
                .orElseThrow(CWrongApproach::new);

        updateValid(activeAccessToken, true);
        return activeAccessToken;
    }

    public ActiveAccessToken getAnotherActiveToken(User user, ActiveAccessToken activeToken) {
        if (activeToken.getConflict() == ActiveStatus.NO_CONFLICT.ordinal()) {
            return null;
        }
        if (activeToken.getConflict() == ActiveStatus.PRE_CONFLICTED.ordinal()) {
            return activeAccessTokenRedisRepository
                    .findByUserIdAndConflict(user.getId(), ActiveStatus.POST_CONFLICTED.ordinal())
                    .orElseThrow(CUserNotActiveException::new);
        }
        return activeAccessTokenRedisRepository
                .findByUserIdAndConflict(user.getId(), ActiveStatus.PRE_CONFLICTED.ordinal())
                .orElseThrow(CUserNotActiveException::new);
    }

    public void resolveConflict(ResolveConflictRequestDto resolveConflictRequestDto) {
        Authentication authentication = jwtProvider.getAuthentication(resolveConflictRequestDto.getAccessToken());
        User user = (User) authentication.getPrincipal();
        ActiveAccessToken currentAccessToken = activeAccessTokenRedisRepository
                .findById(resolveConflictRequestDto.getAccessToken())
                .orElseThrow(CUserNotFoundException::new);
        ActiveAccessToken otherAccessToken = activeAccessTokenRedisRepository
                .findByUserIdAndConflict(user.getId(), 2)
                .orElseThrow(CUserNotFoundException::new);
        if (currentAccessToken.getConflict() != ActiveStatus.PRE_CONFLICTED.ordinal()) {
            throw new CWrongTypeTokenException();
        }

        if (resolveConflictRequestDto.isKeepGoing()) {
            updateConflictStatus(currentAccessToken, ActiveStatus.POST_CONFLICTED.ordinal());
            updateConflictStatus(otherAccessToken, ActiveStatus.PRE_CONFLICTED.ordinal());
        } else {
            activeAccessTokenRedisRepository.delete(currentAccessToken);
            updateConflictStatus(otherAccessToken, ActiveStatus.NO_CONFLICT.ordinal());
        }
    }
}
