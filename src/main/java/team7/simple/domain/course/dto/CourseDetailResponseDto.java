package team7.simple.domain.course.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import team7.simple.domain.course.entity.Course;

@Getter
@NoArgsConstructor
public class CourseDetailResponseDto {

    private Long courseId;
    private String title;
    private String subtitle;

    private String instructor;
    private int attendeeCount;

    public CourseDetailResponseDto(Course course, int attendeeCount) {
        this.courseId = course.getId();
        this.title = course.getTitle();
        this.subtitle = course.getSubtitle();
        this.instructor = course.getInstructor().getAccount();
        this.attendeeCount = attendeeCount;
    }

}
