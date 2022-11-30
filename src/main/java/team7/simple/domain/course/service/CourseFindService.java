package team7.simple.domain.course.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.course.error.exception.CCourseNotFoundException;
import team7.simple.domain.course.repository.CourseJpaRepository;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CourseFindService {
    private final CourseJpaRepository courseJpaRepository;

    @Transactional
    public Course getCourseById(Long id) {
        return courseJpaRepository.findById(id).orElseThrow(CCourseNotFoundException::new);
    }
}
