package toy.project.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.thymeleaf.util.StringUtils;
import toy.project.constant.ItemSellStatus;
import toy.project.entity.Item;
import toy.project.entity.QItem;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @SpringBootTest 통합 테스트를 위해 스프링 부트에서 제공하는 어노테이션으로 모든 Bean을 IoC 컨테이너에 등록함
 * @TestPropertySource 테스트 코드 실행 시 application.properties에 설정해 둔 값보다 application-test.properties에 같은 설정이 있다면 더 높은 순위를 부여함.
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {

    /**
     * 영속성 컨텍스트를 사용하기 위해 @PersistenceContext 어노테이션을 이용해 EntityManager 빈을 주입함.
     */
    @PersistenceContext
    EntityManager em;



    /**
     * ItemRepository를 사용하기 위해서 @Autowired를 통해 의존성 주입 ( Bean )
     */
    @Autowired
    ItemRepository itemRepository;


    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest() {
        Item item = new Item();
        item.setItemName("테스트 상품");
        item.setPrice(100000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        Item savedItem = itemRepository.save(item);
        System.out.println("savedItem.toString() = " + savedItem.toString());
    }


    public void createItemList() {
        for (int i = 1; i <= 10; i++) {
            Item item = new Item();
            item.setItemName("테스트 상품" + i);
            item.setPrice(100000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }
    }


    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNameTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemName("테스트 상품 1");

        for (Item item : itemList) {
            System.out.println("item.toString() = " + item.toString());

        }
    }

    @Test
    @DisplayName("상품명, 상품 상세 설명 or 테스트")
    public void findByItemNameOrItemDetailTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNameOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5");
        for (Item item : itemList) {
            System.out.println("item = " + item.toString());

        }
    }


    @Test
    @DisplayName("가격 LessThan 테스트")
    public void findByPriceLessThanTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThan(100005);
        for (Item item : itemList) {
            System.out.println("item = " + item.toString());

        }
    }

    @Test
    @DisplayName("가격 내림차순 조회 테스트")
    public void findByPriceLessThanOrderByPriceDesc() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(100005);
        for (Item item : itemList) {
            System.out.println("item = " + item.toString());

        }
    }

    @Test
    @DisplayName("@Query를 이용한 상품 조회 테스트")
    public void findByItemDetailTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세 설명");
        for (Item item : itemList) {
            System.out.println("item = " + item.toString());

        }
    }

    @Test
    @DisplayName("nativeQuery 속성을 이용한 상품 조회 테스트")
    public void findByItemDetailByNative() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetailByNative("테스트 상품 상세 설명");
        for (Item item : itemList) {
            System.out.println("item = " + item.toString());

        }
    }

    @Test
    @DisplayName("Querydsl 조회 테스트1")
    public void queryDslTest() {
        this.createItemList();

        //JPAQueryFactory를 이용하여 쿼리를 동적으로 생성합니다. 생성자의 파라미터로는 EntityManager 객체를 넣어줌.
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        //Querydsl을 통해 쿼리를 생성하기 위해 플러그인을 통해 자동으로 생성된 Qitem 객체를 이용함
        QItem qItem = QItem.item;
        //자바 소스코드지만 SQl문과 비슷하게 소스를 작성할 수 있음.
        JPAQuery<Item> query = queryFactory.selectFrom(qItem)
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qItem.itemDetail.like("%" + "테스트 상품 상세 설명" + "%"))
                .orderBy(qItem.price.desc());

        /**
         * JPAQuery 메소드중 하나인 fetch를 이용해서 쿼리 결과를 리스트로 반환함.
         * fetch() 메소드 실행 시점에 쿼리문이 실행되며, JPAQUery에서 결과를 반환함.
         */
        List<Item> itemList = query.fetch();

        for (Item item : itemList) {
            System.out.println("item = " + item.toString());

        }
    }

    /**
     *  상품 데이터를 만드는 새로운 메소드 만듦
     *  1번부터 5번 상품은 상품의 판매 상태를 SELL(판매중)으로 지정하고,
     *  6번부터 10번 상품은 상품의 판매 상태를 SOLD_OUT(품절)로 세팅했음.
     */

    public void createItemList2() {
        for (int i = 1; i <= 5; i++) {
            Item item = new Item();
            item.setItemName("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }

        for (int i = 6; i <= 10; i++) {
            Item item = new Item();
            item.setItemName("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("상품 Querydsl 조회 테스트 2")
    public void queryDslTest2() {

        this.createItemList2();

        /* BooleanBuilder는 쿼리에 들어갈 조건을 만들어주는 빌더로 Predicate를 구현하고 있으며 메소드 체인 형식으로 사용할 수 있음. */
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QItem item = QItem.item;
        String itemDetail = "테스트 상품 상세 설명";
        int price = 10003;
        String itemSellStat = "SELL";

        /* 필요한 상품을 조회하는데 필요한 "and" 조건을 추가하고 있음.
        *  아래 소스에서 상품의 판매 상태가 SELL일 때만 booleanBuilder에 판매 상태 조건을 동적으로 추가하는 것을 볼 수 있음.*/
        booleanBuilder.and(item.itemDetail.like("%" + itemDetail + "%"));
        booleanBuilder.and(item.price.gt(price));

        if (StringUtils.equals(itemSellStat, ItemSellStatus.SELL)) {
            booleanBuilder.and(item.itemSellStatus.eq(ItemSellStatus.SELL));
        }

        /* 데이터를 페이징해 조회하도록 PageRequest.of() 메소드를 이용해 Pageble 객체를 생성.
        *  첫 번째 인자는 조회할 페이지의 번호, 두 번째 인자는 한 페이지당 조회할 데이터의 개수를 넣어줌. ( 1 ~ 5, 6 ~ 10 )*/
        PageRequest pageable = PageRequest.of(0, 5);
        /* QueryDslPredicateExecutor 인터페이스에서 정의한 findAll() 메소드를 이용해 조건에 맞는 데이터를 Page 객체로 받아옴. */
        Page<Item> itemPaingResult = itemRepository.findAll(booleanBuilder, pageable);
        System.out.println("total elements : " + itemPaingResult.getTotalElements());

        List<Item> resultItemList = itemPaingResult.getContent();
        for (Item resultItem : resultItemList) {
            System.out.println("resultItem = " + resultItem.toString());

        }
    }
}