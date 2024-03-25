package toy.project.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Data
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    /* 하나의 상품은 여러 주문 상품으로 들어갈 수 있으므로 주문 상품 기준 다대일 단뱡항 매핑 설정 */
    private Item item;

    @ManyToOne
    @JoinColumn(name = "order_id")
    /* 한 번의 주문에 여러 개의 상품을 주문할 수 있으므로 주문 상품 엔티티와 주문 엔티티를 다대일 단방향 매핑 설정 */
    private Order order;

    private int orderPrice;

    private int count;

    private LocalDateTime regTiem;

    private LocalDateTime updateTiem;

}
