package team7.simple.domain.rating.entity;

import lombok.*;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.user.entity.User;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long ratingId;

    double score;

    @ManyToOne
    @JoinColumn
    User user;

    @ManyToOne
    @JoinColumn
    Course course;
}


