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


    /**
     * 강의를 삭제합니다. 관리자용 API입니다.
     * @param unitId 강의 아이디
     */
    @Transactional
    public void deleteUnit(Long unitId) {
        Unit unit = unitJpaRepository.findById(unitId).orElseThrow(CUnitNotFoundException::new);
        unitJpaRepository.delete(unit);
    }


    /**
     * 강의를 수정합니다. 관리자용 API입니다.
     * @param unitId 강의 아이디
     * @param unitUpdateParam 강의 수정 파라미터 (강의 제목, 강의 설명, 강의 목표)
     * @return 수정된 강의 아이디
     */
    @Transactional
    public Long updateUnit(Long unitId, UnitUpdateParam unitUpdateParam) {
        Unit unit = unitFindService.getUnitById(unitId);
        unit.update(unitUpdateParam.getTitle(), unitUpdateParam.getDescription(), unitUpdateParam.getObjective());
        return unitId;
    }
}
