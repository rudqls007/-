package toy.project.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;
import toy.project.dto.OrderDto;
import toy.project.dto.OrderHistDto;
import toy.project.dto.OrderItemDto;
import toy.project.entity.*;
import toy.project.repository.ItemImgRepository;
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
    private final ItemImgRepository itemImgRepository;


    public Long order(OrderDto orderDto, String loginId) {
        /* 주문할 상품을 조회함. */
        Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException::new);
        /* 현재 로그인한 회원의 이메일 정보를 이용해서 회원 정보를 조회함. */
        Member member = memberRepository.findByLoginId(loginId);

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

    @Transactional(readOnly = true)
    public Page<OrderHistDto> getOrderList(String loginId, Pageable pageable) {

        /* 유저의 loginId와 페이징 조건을 이용하여 주문 목록을 조회함. */
        List<Order> orders = orderRepository.findOrders(loginId, pageable);
        /* 유저의 주문 총 개수를 구함. */
        Long totalCount = orderRepository.countOrder(loginId);

        List<OrderHistDto> orderHistDtos = new ArrayList<>();

        /* 주문 리스트를 순회하면서 구매 이력 페이지에 전달할 DTO를 생성함. */
        for (Order order : orders) {

            OrderHistDto orderHistDto = new OrderHistDto(order);
            List<OrderItem> orderItems = order.getOrderItems();

            for (OrderItem orderItem : orderItems) {

                /* 주문한 페이지의 대표 이미지를 조회 */
                ItemImg itemImg = itemImgRepository
                        .findByItemIdAndRepimgYn(orderItem.getItem().getId(), "Y");
                OrderItemDto orderItemDto = new OrderItemDto(orderItem, itemImg.getImgUrl());

                orderHistDto.addOrderItemDto(orderItemDto);
            }

            orderHistDtos.add(orderHistDto);

        }

        /* 페이지 구현 객체를 생성하여 반환 */
        return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount);
    }

    @Transactional(readOnly = true)
    public boolean validateOrder(Long orderId, String loginId) {
        /* 현재 로그인한 사용자와 주문 데이터를 생성한 사용자가 같은지 검사함 */
        Member curMember = memberRepository.findByLoginId(loginId);
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        Member savedMember = order.getMember();

        /* 같지 않으면 false 반환 */
        if (!StringUtils.equals(curMember.getLoginId(), savedMember.getLoginId())) {
            return false;
        }

        return true;
    }


    public void cancelOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
        /* 주문 취소 상태로 변경하면 변경 감지 기능에 의해서 트랜잭션이 끝날 때 update 쿼리가 실행됨. */
        order.cancelOrder();
    }

    public Long orders(List<OrderDto> orderDtoList, String loginId) {

        Member member = memberRepository.findByLoginId(loginId);
        List<OrderItem> orderItemList = new ArrayList<>();

        /* 주문할 상품 리스트를 만들어 줌 */
        for (OrderDto orderDto : orderDtoList) {
            /* 아이디 체크 로직 */
            Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException::new);

            OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
            orderItemList.add(orderItem);


        }


        /* 현재 로그인한 회원과 주문 상품 목록을 이용하여 주문 엔티티를 만듦*/
        Order order = Order.createOrder(member, orderItemList);
        /* 주문 데이터 저장 */
        orderRepository.save(order);

        return order.getId();

    }
}
