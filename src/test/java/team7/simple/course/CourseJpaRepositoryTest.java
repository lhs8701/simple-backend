package team7.simple.course;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.course.repository.CourseJpaRepository;
import team7.simple.domain.course.service.CourseService;
import team7.simple.domain.user.entity.User;
import team7.simple.domain.user.repository.UserJpaRepository;
import team7.simple.global.error.advice.exception.CCourseNotFoundException;
import team7.simple.global.error.advice.exception.CUserNotFoundException;

import java.util.Collections;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;

@DataJpaTest //@Transactional 포함하고 있음 -> 따로 롤백 안해줘도 됨
@AutoConfigureTestDatabase(replace = Replace.NONE) // 메모리 DB가 아닌, 실제 DB에서 테스트
public class CourseTest {

    @Autowired
    private CourseJpaRepository courseJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;


    @Test
    @DisplayName("강좌 개설 후 조회")
    void 강좌_개설_후_조회() {
        //given
        User user = User.builder()
                .account("testAccount")
                .password("testPassword")
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
        userJpaRepository.save(user);

        Course course = Course.builder()
                .instructor(user)
                .subtitle("testSubTitle")
                .title("testTitle")
                .build();
        Long courseId = courseJpaRepository.save(course).getCourseId();

        //when
        User searchedUser = userJpaRepository.findByAccount("testAccount").orElseThrow(CUserNotFoundException::new);
        Course searchedCourse = courseJpaRepository.findById(courseId).orElseThrow(CCourseNotFoundException::new);

        //then
        assertThat(searchedUser.getAccount()).isEqualTo("testAccount");
        assertThat(searchedCourse.getTitle()).isEqualTo("testTitle");
    }

}
