package team7.simple.domain.course.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.user.entity.User;

@AllArgsConstructor
@Getter
public class CourseRequestDto {
    @NotNull
    private String title;
    private String subtitle;


    public Course toEntity(User instructor) {
        return Course.builder()
                .subtitle(this.subtitle)
                .title(this.title)
                .instructor(instructor)
                .build();
    }
}
