package team7.simple.domain.unit.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.file.entity.Video;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class UnitRequestDto {
    @NotNull
    private String title;
    private String description;
    private String objective;

    public Unit toEntity(Video video, Course course) {
        return Unit.builder()
                .title(this.title)
                .description(this.description)
                .objective(this.objective)
                .video(video)
                .course(course)
                .build();
    }
}
