package team7.simple.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team7.simple.domain.user.entity.User;

public interface UserJpaRepository extends JpaRepository<User,Long> {
}
