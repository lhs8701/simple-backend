package team7.simple.domain.study.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team7.simple.domain.course.entity.Course;

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
