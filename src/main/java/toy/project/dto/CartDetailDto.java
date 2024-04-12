package toy.project.dto;

import lombok.Getter;
import lombok.Setter;


/* 장바구니 조회 페이지에 전달할 CartDetailDto 클래스
*  JPQL로 쿼리 작성 시 생성자를 이용해서 DTO로 바로 반환하는 방법을 사용할 것임. */
@Getter @Setter
public class CartDetailDto {

    //장바구니 상품 아이디
    private Long cartItemId;
    //상품명
    private String itemName;
    //상품 가격
    private int price;
    //상품 수량
    private int count;
    //상품 이미지 경로
    private String imgUrl;

    public CartDetailDto(Long cartItemId, String itemName, int price, int count, String imgUrl) {
        this.cartItemId = cartItemId;
        this.itemName = itemName;
        this.price = price;
        this.count = count;
        this.imgUrl = imgUrl;
    }
}
