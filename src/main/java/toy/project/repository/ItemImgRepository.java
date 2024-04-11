package toy.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.project.entity.ItemImg;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {


    List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);

    /* 상품의 대표 이미지를 찾는 쿼리 메소드 구매 이력 페이지에서 주문 상품의 대표 이미지를 보여주기 위해서 추가함. */
    ItemImg findByItemIdAndRepimgYn(Long itemId, String repimgYn);
}

