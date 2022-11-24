package team7.simple.domain.course.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import team7.simple.domain.course.entity.Course;

/**
 * 사용자가 현재 수강 중인 강좌 내역을 보여줄 때, 사용하는 DTO
 */
@Getter
@NoArgsConstructor
public class JoinedCourseResponseDto {
    private Long courseId;
    private String title;

    public JoinedCourseResponseDto(Course course){
        this.courseId = course.getId();
        this.title = course.getTitle();
    }
}
