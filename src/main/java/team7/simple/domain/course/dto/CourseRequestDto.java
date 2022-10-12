package team7.simple.domain.course.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import team7.simple.domain.course.entity.Course;

@AllArgsConstructor
@Getter
public class CourseRequestDto {
    @NotNull
    private String title;
    private String subtitle;
    @NotNull
    private Long userId;

    public Course toEntity() {
        return Course.builder()
                .subtitle(this.subtitle)
                .title(this.title)
                .build();
    }
}
