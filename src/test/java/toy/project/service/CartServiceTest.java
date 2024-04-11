package toy.project.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import toy.project.constant.ItemSellStatus;
import toy.project.dto.CartItemDto;
import toy.project.entity.CartItem;
import toy.project.entity.Item;
import toy.project.entity.Member;
import toy.project.repository.CartItemRepository;
import toy.project.repository.ItemRepository;
import toy.project.repository.MemberRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class CartServiceTest {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CartService cartService;

    @Autowired
    CartItemRepository cartItemRepository;

    /* 장바구니에 담을 상품 */
    public Item saveItem() {
        Item item = new Item();
        item.setItemName("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        return itemRepository.save(item);
    }


    /* 회원 정보 저장 */
    public Member saveMember() {
        Member member = new Member();
        member.setLoginId("qwer123");
        return memberRepository.save(member);
    }

    @Test
    @DisplayName("장바구니 담기 테스트")
    public void addCart() {
        Item item = saveItem();
        Member member = saveMember();
        CartItemDto cartItemDto = new CartItemDto();
        /* 장바구니에 담을 수량 : 5 */
        cartItemDto.setCount(5);
        /* 장바구니에 담을 상품 */
        cartItemDto.setItemId(item.getId());

       /* 상품을 장바구니에 담는 로직 호출 결과 생성된 장바구니 상품 아이디를 cartItemID 변수에 저장 */
        Long cartItemId = cartService.addCart(cartItemDto, member.getLoginId());

        /* 장바구니 상품 아이디를 이용하여 생성된 장바구니 상품 정보 조회 */
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);

        /* 상품 아이디와 장바구니에 저장된 아이디 비교 */
        assertEquals(item.getId(), cartItem.getItem().getId());
        /* 장바구니에 담았던 실제 수량과 장바구니에 저장된 수량이 같은지 비교 */
        assertEquals(cartItemDto.getCount(), cartItem.getCount());

    }

}