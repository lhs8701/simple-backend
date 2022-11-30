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
     * 해당 사용자의 계정으로 접속 중인 레디스 액티브 토큰이 있는지 확인하고, 충돌 상태를 리턴합니다.
     * 만약, 충돌이 발생할 경우, 기존 레디스 액티브 토큰의 충돌 상태도 갱신합니다.
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
     * 레디스 액티브 토큰의 충돌 상태를 갱신합니다.
     * Redis Repository에서 setter가 제대로 동작하지 않아서, 작성한 메소드입니다.
     * @param token 레디스 액티브 토큰
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


    /**
     * 최근 접속한 계정의 레디스 액티브 토큰을 반환합니다.
     * @param user 사용자
     * @return 최근 접속한 레디스 액티브 토큰
     */
    public ActiveAccessToken getLatestActiveToken(User user) {
        ActiveAccessToken activeAccessToken = activeAccessTokenRedisRepository
                .findByUserIdAndValid(user.getId(), false)
                .orElseThrow(CWrongApproach::new);

        updateValid(activeAccessToken, true);
        return activeAccessToken;
    }


    /**
     * 레디스 액티브 토큰의 유효성을 변경합니다.
     * 유효성은 최근 접속한 레디스 액티브 토큰을 가려내기 쉽도록 도입한 장치입니다.
     * @param token 레디스 액티브 토큰
     * @param valid 유효성 (true/false)
     */
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


    /**
     * 레디스에서, 해당 계정에 대한 또다른 레디스 액티브 토큰을 찾아서 반환합니다. (인자로 넘어온 레디스 액티브 토큰 이외의)
     * 만약, 해당 계정에 대한 레디스 액티브 토큰이 파라미터로 넘어온 토큰 한개뿐이라면, null을 반환합니다.
     * @param user 사용자
     * @param activeToken 레디스 액티브 토큰
     * @return 또다른 레디스 액티브 토큰
     */
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


    /**
     * 시청 충돌이 발생했을 때, 사용자의 선택에 따라 시청을 계속하거나, 종료합니다.
     * 파라미터로 넘어온 ResolveConflictRequestDto의 keepGoing이 참일 경우, API를 호출한 사용자가 시청을 이어나갑니다.
     * 그렇지 않을 경우, 시청을 중지합니다.
     * @param resolveConflictRequestDto 액세스 토큰, 시청 유지 여부
     */
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
