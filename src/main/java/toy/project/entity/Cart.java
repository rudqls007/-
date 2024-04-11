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


    /* 회원 한 명당 1개의 장바구니를 갖으므로 처음 장바구니에 상품을 담을 때는 해당 회원의 장바구니를 생성해줘야 함.
    *  회원 엔티티를 파라미터로 받아 장바구니 엔티티를 생성하는 로직 */
    public static Cart createCart(Member member) {
        Cart cart = new Cart();
        cart.setMember(member);
        return cart;
    }
}
