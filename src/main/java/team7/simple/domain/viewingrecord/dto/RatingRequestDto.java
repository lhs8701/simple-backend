package team7.simple.domain.viewingrecord.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
public class RatingRequestDto {
    @NotNull
    double score;
}
