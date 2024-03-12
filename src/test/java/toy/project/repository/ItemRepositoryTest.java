package toy.project.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import toy.project.constant.ItemSellStatus;
import toy.project.entity.Item;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @SpringBootTest
 * 통합 테스트를 위해 스프링 부트에서 제공하는 어노테이션으로 모든 Bean을 IoC 컨테이너에 등록함
 * @TestPropertySource
 * 테스트 코드 실행 시 application.properties에 설정해 둔 값보다 application-test.properties에 같은 설정이 있다면 더 높은 순위를 부여함.
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {


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
}