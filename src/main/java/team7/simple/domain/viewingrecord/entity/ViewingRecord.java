package team7.simple.domain.viewingrecord.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import team7.simple.domain.unit.entity.Unit;
import team7.simple.domain.user.entity.User;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert //insert 시 null인 필드 제외
@DynamicUpdate //update 시 null인 필드 제외
public class ViewingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;

    String time;

    @ColumnDefault("false")
    boolean check;

    @ManyToOne
    @JoinColumn
    User user;

    @ManyToOne
    @JoinColumn
    Unit unit;
}
