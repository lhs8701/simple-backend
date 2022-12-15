package team7.simple.domain.course.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.enroll.entity.Enroll;

import java.time.LocalDateTime;

/**
 * 사용자가 현재 수강 중인 강좌 내역을 보여줄 때, 사용하는 DTO
 */
@Getter
@NoArgsConstructor
public class JoinedCourseResponseDto {
    private Long courseId;
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime joinedDate;

    public JoinedCourseResponseDto(Enroll enroll){
        this.courseId = enroll.getCourse().getId();
        this.title = enroll.getCourse().getTitle();
        this.joinedDate = enroll.getCreatedDate();
    }
}
