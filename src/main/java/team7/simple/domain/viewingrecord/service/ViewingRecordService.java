package team7.simple.domain.viewingrecord.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.user.entity.User;
import team7.simple.domain.viewingrecord.entity.ViewingRecord;
import team7.simple.domain.viewingrecord.repository.ViewingRecordJpaRepository;

@Service
@RequiredArgsConstructor
public class ViewingRecordService {

    private final ViewingRecordJpaRepository viewingRecordJpaRepository;

    public double getTimeline(User user, Unit unit) {
        ViewingRecord viewingRecord = viewingRecordJpaRepository.findByUnitAndUser(unit, user).orElse(null);
        if (viewingRecord == null) {
            return 0;
        }
        return viewingRecord.getTimeline();
    }

    public boolean doesCompleted(User user, Unit unit) {
        ViewingRecord viewingRecord = viewingRecordJpaRepository.findByUnitAndUser(unit, user).orElse(null);
        return viewingRecord != null && viewingRecord.isCompleted();
    }
}
