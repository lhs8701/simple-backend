//package team7.simple.record;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import team7.simple.domain.record.entity.Record;
//import team7.simple.domain.record.service.RecordService;
//import team7.simple.domain.unit.entity.Unit;
//import team7.simple.domain.unit.repository.UnitJpaRepository;
//import team7.simple.domain.user.entity.User;
//import team7.simple.domain.user.repository.UserJpaRepository;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = SpringApplication.class)
//@DataJpaTest
//public class RecordJpaTest {
//
//    @Autowired
//    private RecordService recordService;
//
//    @Autowired
//    private UserJpaRepository userJpaRepository;
//
//    @Autowired
//    private UnitJpaRepository unitJpaRepository;
//
//    @Test
//    @DisplayName("시청_기록을_저장하고_조회한다.")
//    public void 시청_기록을_저장하고_조회한다() {
//        //given
//        User user = new User();
//        Unit unit = new Unit();
//        Long savedUnitId = unitJpaRepository.save(unit).getUnitId();
//        String savedUserId = userJpaRepository.save(user).getUserId();
//
//        //when
//        recordService.saveRecord(unit, user, 1.234, true);
//
//        //then
//        Record record = recordService.getRecordByUnitAndUser(unit, user).orElse(null);
//        assert record != null;
//        assertThat(record.getUser().getUserId()).isEqualTo(savedUserId);
//        assertThat(record.getUnit().getUnitId()).isEqualTo(savedUnitId);
//        assertThat(record.getRating()).isEqualTo(1.234);
//        assertThat(record.isCompleted()).isEqualTo(true);
//    }
//}
