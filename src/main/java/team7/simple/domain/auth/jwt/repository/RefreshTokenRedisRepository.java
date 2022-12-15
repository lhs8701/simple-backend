package team7.simple.domain.auth.jwt.repository;

import org.springframework.data.repository.CrudRepository;
import team7.simple.domain.auth.jwt.entity.RefreshToken;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {
}
