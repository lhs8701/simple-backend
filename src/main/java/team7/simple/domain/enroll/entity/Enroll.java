package team7.simple.domain.enroll.entity;

import lombok.*;
import team7.simple.domain.course.entity.Course;
import team7.simple.domain.user.entity.User;
import team7.simple.global.common.jpa.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Enroll extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToOne
    @JoinColumn
    private Course course;
}
