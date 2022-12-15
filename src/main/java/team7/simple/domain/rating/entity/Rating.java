package team7.simple.domain.rating.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.user.entity.User;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double score;

    private String comment;

    @ManyToOne
    @JoinColumn
    private Unit unit;

    @ManyToOne
    @JoinColumn
    private User user;

    public void update(double score, String comment){
        this.score = score;
        this.comment = comment;
    }
}
