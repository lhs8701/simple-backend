package team7.simple.domain.auth.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import team7.simple.domain.auth.jwt.entity.ActiveAccessToken;

import java.util.Optional;

public interface ActiveAccessTokenRedisRepository extends CrudRepository<ActiveAccessToken, String> {
    Optional<ActiveAccessToken> findByUserId(Long userId);
    void deleteByUserId(Long userId);
}