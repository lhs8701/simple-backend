package team7.simple.domain.unit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team7.simple.domain.unit.entity.Unit;

import java.util.List;
import java.util.Optional;

public interface UnitJpaRepository extends JpaRepository<Unit, Long> {

    Optional<List<Unit>> findAllById(Long unitId);
}
