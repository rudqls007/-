package toy.project.entity;

import jakarta.persistence.*;
import lombok.Data;
import toy.project.constant.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* 정렬할 때 사용하는 order 키워드가 있기에 orders로 지정 */
@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    /* 한 명의 회원은 여러 번 주문을 할 수 있으므로 다대일 단방향 매핑 */
    private Member member;

    /* 주문일 */
    private Locale orderDate;

    /* 주문 상태 */
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    /* @OneToMany(mappedBy = "order") : 주문 상품 엔티티와 일대다 매핑을 함.
    *  외래키(order_id)가 order_item 테이블에 있으므로 연관 관계의 주인은 OrderItem 엔티티임.
    *  Order 엔티티가 주인이 아니므로 "mappedBy 속성으로 연관 관계의 주인을 설정함.
    *  속성의 값으로 order를 적어준 이유는 OrderItem에 있는 Order에 의해 관리된다는 의미로 해석하면 되고,
    *  연관 관계의 주인의 필드인 order를 mappedBy의 값으로 세팅하면 됨.
    *  cascade = CascadeType.ALL : 부모 엔티티의 영속성 상태 변화를 자식 엔티티에 모두 전이함. */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    /* 하나의 주문이 여러 개의 주문 상품을 갖으므로 List 자료형을 사용해서 매핑함. */
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime regTiem;

    private LocalDateTime updateTiem;


}
