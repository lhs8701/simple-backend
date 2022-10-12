package team7.simple.domain.course.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.unit.dto.UnitResponseDto;
import team7.simple.domain.unit.entity.Unit;

import java.util.List;

@Getter
@NoArgsConstructor
public class CourseResponseDto {

    private Long courseId;
    private String title;
    private String subtitle;
    private List<UnitResponseDto> unitList;
//    private String instructorName;
//    private Double ratingScore;
//    private Integer attendeeCount;

    public CourseResponseDto(Course course, List<UnitResponseDto> unitList) {
        this.courseId = course.getCourseId();
        this.title = course.getTitle();
        this.subtitle = course.getSubtitle();
        this.unitList = unitList;
    }

}
