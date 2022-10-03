package team7.simple.domain.auth.jwt.repository;

import org.springframework.data.repository.CrudRepository;
import team7.simple.domain.auth.jwt.entity.LogoutAccessToken;

public interface LogoutAccessTokenRedisRepository extends CrudRepository<LogoutAccessToken, String> {
}
