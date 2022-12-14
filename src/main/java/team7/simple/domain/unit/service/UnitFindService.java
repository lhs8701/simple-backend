package team7.simple.domain.unit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.unit.error.exception.CUnitNotFoundException;
import team7.simple.domain.unit.repository.UnitJpaRepository;

@RequiredArgsConstructor
@Service
public class UnitFindService {

    private final UnitJpaRepository unitJpaRepository;

    @Transactional
    public Unit getUnitById(Long unitId) {
        return unitJpaRepository.findById(unitId).orElseThrow(CUnitNotFoundException::new);
    }
}
