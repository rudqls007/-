package toy.project.dto;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MainItemDto {

    private Long id;

    private String itemName;

    private String itemDetail;

    private String imgUrl;

    private Integer price;

    /* 생성자에 @QueryProjection 어노테이션을 선언하여 QueryDsl로 결과 조회 시
       Entity를 DTo로 변환하지 않고 MainItemDto 객체로 바로 받아올 수 있음. */
    @QueryProjection
    public MainItemDto(Long id, String itemName, String itemDetail, String imgUrl, Integer price) {
        this.id = id;
        this.itemName = itemName;
        this.itemDetail = itemDetail;
        this.imgUrl = imgUrl;
        this.price = price;
    }
}
