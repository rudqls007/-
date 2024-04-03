package toy.project.config;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @EntityListeners(value = {AuditingEntityListener.class}) : Auditing 적용 하기 위한 어노테이션 적용
 * @MappedSuperclass = 공통 매핑 정보가 필요할 때 사용하는 어노테이션으로 부모 클래스를 상속 받는 자식 클래스에 매핑 정보만 제공함.
 * @CreatedDate = 엔티티가 생성되어 저장될 때 시간을 자동으로 저장
 * @LastModifiedDate = 엔티티의 값을 변경할 때 시간을 자동으로 저장
 */
@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter @Setter
public abstract class BaseTimeEntity {

    @CreatedDate
    @Column(updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd' 'HH:mm:ss")
    private LocalDateTime regTime;

    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd' 'HH:mm:ss")
    private LocalDateTime updateTime;
}
