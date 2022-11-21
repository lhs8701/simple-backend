package team7.simple.domain.record.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.user.entity.User;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double timeline;

    @ColumnDefault("false")
    private boolean completed;

    double rating;

    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToOne
    @JoinColumn
    private Unit unit;
}