package toy.project.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.project.dto.OrderDto;
import toy.project.entity.Item;
import toy.project.entity.Member;
import toy.project.entity.Order;
import toy.project.entity.OrderItem;
import toy.project.repository.ItemRepository;
import toy.project.repository.MemberRepository;
import toy.project.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {


    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;


    public Long order(OrderDto orderDto, String email) {
        /* 주문할 상품을 조회함. */
        Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException::new);
        /* 현재 로그인한 회원의 이메일 정보를 이용해서 회원 정보를 조회함. */
        Member member = memberRepository.findByEmail(email);

        List<OrderItem> orderItemList = new ArrayList<>();
        /* 주문할 상품 엔티티와 주문 수량을 이용하여 주문 상품 엔티티를 생성 */
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        orderItemList.add(orderItem);

        /* 회원 정보와 주문할 상품 리스트 정보를 이용하여 주문 엔티티를 생성 */
        Order order = Order.createOrder(member, orderItemList);
        /* 생성한 주문 엔티티를 저장 */
        orderRepository.save(order);

        return order.getId();

    }

    public void cancelOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
        order.cancelOrder();
    }
}
