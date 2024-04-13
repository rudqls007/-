package toy.project.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CartOrderDto {

    private Long cartItemId;
    /* 장바구니에서 여러 개의 상품을 주문하기에 CartOrderDto 클래스를 자기 자신으로 List를 가지고 있도록 만듦 */
    private List<CartOrderDto> cartOrderDtoList;
}
