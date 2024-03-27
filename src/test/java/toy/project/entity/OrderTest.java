package toy.project.entity;

import groovy.util.logging.Slf4j;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import toy.project.constant.ItemSellStatus;
import toy.project.repository.ItemRepository;
import toy.project.repository.MemberRepository;
import toy.project.repository.OrderItemRepository;
import toy.project.repository.OrderRepository;

import java.time.LocalDateTime;


@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
class OrderTest {




    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    OrderItemRepository orderItemRepository;


    @PersistenceContext
    EntityManager em;


    public Item createItem() {
        Item item = new Item();
        item.setItemName("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        return item;

    }

    @Test
    @DisplayName("영속성 전이 테스트")
    public void cascadeTest() {

        Order order = new Order();

        for (int i = 0; i < 3; i++) {
            Item item = this.createItem();
            itemRepository.save(item);
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);
            /* 아직 영속성 컨텍스트에 저장되지 않은 orderItem 엔티티를 order 엔티티에 담아줌. */
            order.getOrderItems().add(orderItem);

        }

        /* order 엔티티를 저장하면서 강제로 flush를 호출하여 영속성 컨텍스트에 있는 객체들을 데이터베이스에 반영함. */
        orderRepository.saveAndFlush(order);
        /* 영속성 컨텍스트의 상태를 초기화 */
        em.clear();

        /* 영속성 컨텍스트를 초기화 했기 때문에 데이터베이스에서 주문 엔티티를 조회함.
         *  select 쿼리문이 실행됨. */
        Order savedOrder = orderRepository.findById(order.getId()).orElseThrow(EntityNotFoundException::new);

        /* itemOrder 엔티티 3개가 실제로 데이터베이스에 저장되었는지 검사함. */
        Assertions.assertEquals(3, savedOrder.getOrderItems().size());

    }


    public Order createOrder() {
        Order order = new Order();

        for (int i = 0; i < 3; i++) {
            Item item = createItem();
            itemRepository.save(item);
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);
            /* 아직 영속성 컨텍스트에 저장되지 않은 orderItem 엔티티를 order 엔티티에 담아줌. */
            order.getOrderItems().add(orderItem);

        }

        Member member = new Member();
        memberRepository.save(member);

        order.setMember(member);
        orderRepository.save(order);
        return order;


    }

    @Test
    @DisplayName("고아 객체 제거 테스트")
    public void orphanRemovalTest() {
        Order order = this.createOrder();
        order.getOrderItems().remove(0);
        em.flush();
    }


    @Test
    @DisplayName("지연 로딩 테스트")
    public void lazyLoadingTest() {
        Order order = this.createOrder();
        Long orderItemId = order.getOrderItems().get(0).getId();
        /*  영속성 컨텍스트 상태 초기화 */
        em.flush();
        em.clear();

        /* 영속성 컨텍스트의 상태 초기화 후 order 엔티티에 저장했던 주문 상품 아이디를 이용하여 orderItem을 데이터베이스에 다시 조화 */
        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElseThrow(EntityNotFoundException::new);
        /* orderItem 엔티티에 있는 order 객체의 클래스를 출력하고, Order 클래스가 출력되는 것을 확인할 수 있음. */
        System.out.println("Order class = " + orderItem.getOrder().getClass());
        System.out.println("================================================");
        orderItem.getOrder().getOrderDate();
        System.out.println("================================================");
    }
}