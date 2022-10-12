package team7.simple.domain.course.dto;

import lombok.Getter;
import team7.simple.domain.course.entity.Course;

@Getter
public class CourseResponseDto {

    private Long courseId;
    private String title;
    private String subtitle;
    private String instructorName;
    private Double ratingScore;
    private Integer attendeeCount;

    public CourseResponseDto(Course course) {
        this.courseId = course.getCourseId();
        this.title = course.getTitle();
        this.subtitle = course.getSubtitle();

    }

}
