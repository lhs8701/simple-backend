package team7.simple.domain.rating.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import team7.simple.domain.rating.entity.Rating;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
public class RatingDetailResponseDto {
    @NotNull
    double score;

    String comment;

    String userAccount;

    public RatingDetailResponseDto(Rating rating){
        this.score = rating.getScore();
        this.comment = rating.getComment();
        this.userAccount = rating.getUser().getAccount();
    }
}
