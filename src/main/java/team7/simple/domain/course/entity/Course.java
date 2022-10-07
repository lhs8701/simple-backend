package team7.simple.domain.course.entity;

import lombok.*;
import team7.simple.domain.unit.entity.Unit;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;
    private String title;
    private String subtitle;

    @Builder.Default
    @OneToMany(mappedBy = "Course", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Unit> unitList = new ArrayList<Unit>();

    @Builder
    public Course(Long id, String title, String subtitle) {
        this.courseId = id;
        this.title = title;
        this.subtitle = subtitle;

    }
}
