package team7.simple.domain.unit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.simple.domain.unit.dto.UnitUpdateParam;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.unit.error.exception.CUnitNotFoundException;
import team7.simple.domain.unit.repository.UnitJpaRepository;

@RequiredArgsConstructor
@Service
public class UnitAdminService {

    private final UnitJpaRepository unitJpaRepository;
    private final UnitFindService unitFindService;

    @Transactional
    public void deleteUnit(Long unitId) {
        Unit unit = unitJpaRepository.findById(unitId).orElseThrow(CUnitNotFoundException::new);
        unitJpaRepository.delete(unit);
    }

    @Transactional
    public Long updateUnit(Long unitId, UnitUpdateParam unitUpdateParam) {
        Unit unit = unitFindService.getUnitById(unitId);
        unit.update(unitUpdateParam.getTitle(), unitUpdateParam.getDescription(), unitUpdateParam.getObjective());
        return unitId;
    }
}
