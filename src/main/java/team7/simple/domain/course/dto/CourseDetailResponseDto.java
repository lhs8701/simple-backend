package team7.simple.domain.course.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.unit.dto.UnitThumbnailResponseDto;

import java.util.List;

@Getter
@NoArgsConstructor
public class CourseDetailResponseDto {

    private Long courseId;
    private String title;
    private String subtitle;

    private String instructor;
    private double rating;
    private int attendeeCount;
    private List<UnitThumbnailResponseDto> unitList;

    public CourseDetailResponseDto(Course course, int attendeeCount, List<UnitThumbnailResponseDto> unitList) {
        this.courseId = course.getCourseId();
        this.title = course.getTitle();
        this.subtitle = course.getSubtitle();
        this.instructor = course.getInstructor().getAccount();
        this.attendeeCount = attendeeCount;
        this.unitList = unitList;
    }

}
