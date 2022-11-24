package team7.simple.domain.course.entity;

import lombok.*;
import team7.simple.domain.enroll.entity.Enroll;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.user.entity.User;
import team7.simple.global.common.jpa.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Course extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String subtitle;

    @ManyToOne
    @JoinColumn
    private User instructor;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Unit> unitList = new ArrayList<>();

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)

    private List<Enroll> enrollList = new ArrayList<>();
}
