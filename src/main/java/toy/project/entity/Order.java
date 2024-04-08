package toy.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import toy.project.config.BaseEntity;
import toy.project.constant.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* 정렬할 때 사용하는 order 키워드가 있기에 orders로 지정 */
@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
public class Order extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    /* 한 명의 회원은 여러 번 주문을 할 수 있으므로 다대일 단방향 매핑 */
    private Member member;

    /* 주문일 */
    private LocalDateTime orderDate;

    /* 주문 상태 */
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    /* @OneToMany(mappedBy = "order") : 주문 상품 엔티티와 일대다 매핑을 함.
    *  외래키(order_id)가 order_item 테이블에 있으므로 연관 관계의 주인은 OrderItem 엔티티임.
    *  Order 엔티티가 주인이 아니므로 "mappedBy 속성으로 연관 관계의 주인을 설정함.
    *  속성의 값으로 order를 적어준 이유는 OrderItem에 있는 Order에 의해 관리된다는 의미로 해석하면 되고,
    *  연관 관계의 주인의 필드인 order를 mappedBy의 값으로 세팅하면 됨.
    *  cascade = CascadeType.ALL : 부모 엔티티의 영속성 상태 변화를 자식 엔티티에 모두 전이함. */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    /* 하나의 주문이 여러 개의 주문 상품을 갖으므로 List 자료형을 사용해서 매핑함. */
    private List<OrderItem> orderItems = new ArrayList<>();

//    private LocalDateTime regTiem;
//
//    private LocalDateTime updateTiem;



    public void addOrderItem(OrderItem orderItem) {
        /* orderItems에는 주문 상품 정보들을 담아주고 orderItem 객체를 order 객체의 orderItems에 추가함. */
        orderItems.add(orderItem);
        /* Order 엔티티와 OrderItem 엔티티가 양방향 참조 관계이므로, orderItem 객체에도 order 객체를 세팅함. */
        orderItem.setOrder(this);
    }

    public static Order createOrder(Member member, List<OrderItem> orderItemList) {
        Order order = new Order();
        /* 상품을 주문한 회원의 정보를 세팅 */
        order.setMember(member);
        /* 상품 페이지에서는 1개의 상품을 주문하지만, 장바구니 페이지에서는 한 번에 여러 개의 상품을 주문할 수도 있음.
        *  따라서 여러 개의 주문 상품을 담을 수 있도록 리스트 형태의 파라미터 값으로 받으며, 주문 객체에 orderItem 객체를 추가. */
        for (OrderItem orderItem : orderItemList) {
            order.addOrderItem(orderItem);
        }
        /* 주문 상태를 ORDER로 세팅 */
        order.setOrderStatus(OrderStatus.ORDER);
        /* 현재 시간을 주문 시간으로 세팅 */
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    /* 총 주문 금액을 구하는 메소드 */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();

        }
        return totalPrice;
    }


    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCEL;
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

}
