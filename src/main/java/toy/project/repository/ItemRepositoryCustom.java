package toy.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toy.project.dto.ItemSearchDto;
import toy.project.entity.Item;

public interface ItemRepositoryCustom {

    /* 상품 조회 조건을 담고 있는 ItemSearchDto 객체와 페이징 정보를 담고 있는 pageable 객체를 파라미터로 받는 getAdminItemPage 메소드를 정의
    *  반환 데이터로 Page<Item> 객체를 반환함. */
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
