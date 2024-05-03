package toy.project.dto;

import lombok.Data;
import org.modelmapper.ModelMapper;
import toy.project.entity.ItemImg;

@Data
public class ItemImgDto {

    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;

    /* 멤버 변수로 ModelMapper 객체 추가 */
    private static ModelMapper modelMapper = new ModelMapper();

    /* ItemImg 엔티티 객체를 파라미터로 받아서 ItemImg 객체의 자료형과 멤버변수의 이름이 같을 때 ItemImgDto로 값을 복사해서 반환함.
    *  static 메소드로 선언해 ItemImgDto 객체를 생성하지 않아도 호출할 수 있도록 함. */
    public static ItemImgDto of(ItemImg itemImg) {

        return modelMapper.map(itemImg, ItemImgDto.class);
    }
}
