package toy.project.dto;


import lombok.Getter;
import lombok.Setter;
import toy.project.entity.OrderItem;

@Getter @Setter
public class OrderItemDto {

    /* OrderItemDto 클래스의 생성자로 orderItem 객체와 이미지 경로를 파라미터로 받아서 멤버 변수 값을 세팅함. */
    public OrderItemDto(OrderItem orderItem, String imgUrl){
        this.itemName = orderItem.getItem().getItemName();
        this.count = orderItem.getCount();
        this.orderPrice = orderItem.getOrderPrice();
        this.imgUrl = imgUrl;
    }

    private String itemName; //상품명
    private int count; //주문 수량

    private int orderPrice; //주문 금액
    private String imgUrl; //상품 이미지 경로

}
