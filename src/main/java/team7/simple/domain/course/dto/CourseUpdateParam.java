package team7.simple.domain.course.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CourseUpdateParam {
    @NotNull
    private String title;
    @NotNull
    private String subtitle;
}
