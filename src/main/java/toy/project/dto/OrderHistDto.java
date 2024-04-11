package toy.project.dto;

import lombok.Getter;
import lombok.Setter;
import toy.project.constant.OrderStatus;
import toy.project.entity.Order;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderHistDto {

    /* OrderHistDto 클래스의 생성자로 order 객체를 파라미터로 받아서 멤버 변수 값을 세팅
    *  주문 날짜의 경우 화면에 "yyyy-MM-dd HH:mm 형태로 전달하기 위해서 포멧을 수정*/
    public OrderHistDto(Order order) {
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.orderStatus = order.getOrderStatus();
    }

    private Long orderId;

    private String orderDate;

    private OrderStatus orderStatus;

    private List<OrderItemDto> orderItemDtoList = new ArrayList<>();

    /* orderItemDto 객체를 주문 상품 리스트에 추가하는 메소드 */
    public void addOrderItemDto(OrderItemDto orderItemDto) {
        orderItemDtoList.add(orderItemDto);
    }
}
