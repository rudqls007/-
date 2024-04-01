package toy.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import toy.project.entity.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item>, ItemRepositoryCustom{

    //상품명 조회
    List<Item> findByItemName(String itemName);

    //상품명과 상품 상세 설명을 OR 조건을 이용하여 조회하는 쿼리 메소드
    List<Item> findByItemNameOrItemDetail(String itemName, String itemDetaill);

    //파라미터로 넘어온 price 변수보다 값이 작은 상품 데이터를 조회하는 쿼리 메소드
    List<Item> findByPriceLessThan(Integer price);

    //OrderBy를 이용한 상품 가격 정렬
    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    /**
     * @Query를 이용한 검색 처리하기
     *
     * @Query 어노테이션 안에 JPQL로 작성한 쿼리문을 넣고 from 뒤에는 엔티티 클래스로 작성한 item을 지정해주었고,
     * item으로부터 데이터를 select하겠다는 것을 의미함.
     *
     * 파라미터에 @Param 어노테이션을 이용하여 파라미터로 넘옹ㄴ 값을 JPQL에 들어갈 변수로 지정해줄 수 있음.
     * 현재는 itemDetail 변수를 "like % %" 사이에 "itemDetail"로 값이 들어가도록 작성함.
     */

    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

    /**
     * 기존의 데이터베이스에서 사용하던 쿼리를 사용해야 할 때는 @Query의 nativeQuery 속성을 사용하면 기존 쿼리를 그대로 사용 가능함.
     * 하지만 특정 데이터베이스에 종속되는 쿼리문을 사용하기 때문에 데이터베이스에 독립적이라는 장점을 잃어버림.
     * 복잡한 쿼리를 그대로 사용해야 하는 경우에 주로 사용함.
     */
    @Query(value = "select * from item i where i.item_detail like %:itemDetail% order by  i.price desc", nativeQuery = true)
    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);
}
