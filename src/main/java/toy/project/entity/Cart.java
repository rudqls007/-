package toy.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import toy.project.config.BaseEntity;

@Entity
@Table(name = "cart")
@Getter @Setter @ToString
public class Cart extends BaseEntity {

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /* 회원 엔티티와 일대일 매핑 */
    @OneToOne(fetch = FetchType.LAZY)
    /* JoinColumn 어노테이션을 이용해 매핑할 외래키를 지정하고 name 속성에는 매핑할 외래키의 이름을 설정함.
    *  직접 지정하는 게 좋음. */
    @JoinColumn(name = "member_id")
    private Member member;
}
