package team7.simple.domain.viewingrecord.service;

import org.springframework.stereotype.Service;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.user.entity.User;

@Service
public class ViewingRecordService {

    public double getTimeline(User user, Unit unit){
        return 0;
    }

    public boolean doesCompleted(User user, Unit unit){
        return false;
    }
}
