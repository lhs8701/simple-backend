package team7.simple.domain.course.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String subtitle;

    @Builder
    public Course(Long id, String title, String subtitle) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;

    }
}
