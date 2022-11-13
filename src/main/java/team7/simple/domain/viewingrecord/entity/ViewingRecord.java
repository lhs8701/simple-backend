package team7.simple.domain.viewingrecord.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert //insert 시 null인 필드 제외
@DynamicUpdate //update 시 null인 필드 제외
@RedisHash("ViewingRecord")
public class ViewingRecord {
    @Id
    private String recordId;

    private double time;

    @ColumnDefault("false")
    private boolean check;

    private String userId;

    @Indexed
    private Long unitId;
}