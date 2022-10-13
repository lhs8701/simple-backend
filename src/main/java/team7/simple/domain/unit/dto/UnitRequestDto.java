package team7.simple.domain.unit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.video.entity.Video;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class UnitRequestDto {

    @NotNull
    private Long courseId;
    @NotNull
    private String title;

    public Unit toEntity(Video video, Course course) {
        return Unit.builder()
                .title(this.title)
                .video(video)
                .course(course)
                .build();
    }
}
