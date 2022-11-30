package team7.simple.domain.course.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.simple.domain.course.dto.CourseUpdateParam;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.course.repository.CourseJpaRepository;

@RequiredArgsConstructor
@Service
public class CourseAdminService {

    private final CourseFindService courseFindService;
    private final CourseJpaRepository courseJpaRepository;

    /**
     * 강좌를 수정합니다. 관리자용 API입니다.
     * @param courseId 수정할 강좌 아이디
     * @param courseUpdateParam 강좌 제목, 강좌 부제목
     * @return 수정된 강좌 아이디
     */
    @Transactional
    public Long updateCourse(Long courseId, CourseUpdateParam courseUpdateParam) {
        Course course = courseFindService.getCourseById(courseId);
        course.update(courseUpdateParam.getTitle(), courseUpdateParam.getSubtitle());
        return courseId;
    }


    /**
     * 강좌를 삭제합니다. 관리자용 API입니다.
     * @param courseId 삭제할 강좌 아이디
     */
    @Transactional
    public void deleteCourse(Long courseId) {
        Course course = courseFindService.getCourseById(courseId);
        courseJpaRepository.delete(course);
    }
}
