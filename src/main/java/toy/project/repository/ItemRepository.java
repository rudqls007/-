package toy.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.project.entity.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    //상품명 조회
    List<Item> findByItemName(String itemName);

    //상품명과 상품 상세 설명을 OR 조건을 이용하여 조회하는 쿼리 메소드
    List<Item> findByItemNameOrItemDetail(String itemName, String itemDetaill);

    //파라미터로 넘어온 price 변수보다 값이 작은 상품 데이터를 조회하는 쿼리 메소드
    List<Item> findByPriceLessThan(Integer price);

    //OrderBy를 이용한 상품 가격 정렬
    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);
}
