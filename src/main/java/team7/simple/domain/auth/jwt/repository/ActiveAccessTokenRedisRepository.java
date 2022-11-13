package team7.simple.domain.auth.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import team7.simple.domain.auth.jwt.entity.ActiveAccessToken;

import java.util.List;
import java.util.Optional;

public interface ActiveAccessTokenRedisRepository extends CrudRepository<ActiveAccessToken, String> {
    Optional<ActiveAccessToken> findByUserId(String userId);
    List<ActiveAccessToken> findAllByUserId(String userId);
}
