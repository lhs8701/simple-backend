package team7.simple.record;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import team7.simple.domain.record.repository.RecordJpaRepository;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.unit.repository.UnitJpaRepository;
import team7.simple.domain.user.entity.User;
import team7.simple.domain.user.repository.UserJpaRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RecordJpaTest {

    @Autowired
    private RecordJpaRepository recordJpaRepository;

    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    UnitJpaRepository unitJpaRepository;

    @Test
    @DisplayName("시청_기록을_저장하고_조회한다.")
    public void 시청_기록을_저장하고_조회한다(){
        User user = new User();
        Unit unit = new Unit();

        unitJpaRepository.save(unit);
        userJpaRepository.save(user);

        Record record =


    }
}
