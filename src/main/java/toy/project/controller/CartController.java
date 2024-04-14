package toy.project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import toy.project.dto.CartDetailDto;
import toy.project.dto.CartItemDto;
import toy.project.dto.CartOrderDto;
import toy.project.service.CartService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {


    private final CartService cartService;

    @PostMapping("/cart")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid CartItemDto cartItemDto,
                                              BindingResult bindingResult, Principal principal) {

        /* 장바구니에 담을 상품 정보를 받는 cartItemDto 객체에 데이터 바인딩 시 에러가 있는지 검증 */
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());

            }

            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        /* 현재 로그인한 회원의 아이디 정보를 변수에 저장 */
        String loginId = principal.getName();
        Long cartItemId;

        try {
            /* 화면으로부터 넘어 온 장바구니에 담을 상품 정보와 현재 로그인한 회원의 이메일 정보를 이용하여
             *  장바구니에 상품을 담는 로직을 호출 */
            cartItemId = cartService.addCart(cartItemDto, loginId);

        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        /* 결과값으로 생성된 장바구니 상품 아이디와 요청이 성공하였다는 HTTP 응답 상태 코드 반환 */
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }


    @GetMapping("/cart")
    public String orderHist(Principal principal, Model model) {
        /* 현재 로그인한 사용자의 아이디 정보를 이용하여 장바구니에 담겨있는 상품 정보를 조회 */
        List<CartDetailDto> cartDetailList = cartService.getCartList(principal.getName());
        System.out.println(principal.getName());
        /* 조회한 장바구니 상품을 뷰에 전달 */
        model.addAttribute("cartItems", cartDetailList);
        return "cart/cartList";
    }

    /* HTTP 메소드에서 PATCH는 요청된 자원의 일부를 업데이트할 때 PATCH를 사용함.
    *  장바구니 상품의 수량만 업테이트하기 떄문에 @PatchMapping 사용 */
    @PatchMapping("/cartItem/{cartItemId}")
    private @ResponseBody ResponseEntity updateCartItem(@PathVariable("cartItemId") Long carItemId, int count, Principal principal) {


        /* 장바구니에 담겨있는 상품의 개수를 0개 이하로 업데이트 요청을 할 때 에러 메세지를 담아서 반환 */
        if (count <= 0) {
            return new ResponseEntity<String>("최소 1개 이상 담아주세요", HttpStatus.BAD_REQUEST);
        /* 로그인한 회원과 카트에 저장된 상품에 대한 회원이 같은지 수정 권한 체크 */
        } else if (!cartService.validateCartItem(carItemId, principal.getName())) {
            return new ResponseEntity<String>("수정할 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        /* 체크 로직이 성공적으로 수행이 되면 장바구니 상품의 개수를 업데이트함. */
        cartService.updateCartItemCount(carItemId, count);
        return new ResponseEntity<Long>(carItemId, HttpStatus.OK);
    }

    /* HTTP 메소드에서 DELETE의 경우 요청된 자원을 삭제할 때 사용함.
    *  장바구니 상품을 삭제하기 떄문에 @DeleteMapping 을 사용하였움. */
    @DeleteMapping("/cartItem/{cartItemId}")
    public @ResponseBody ResponseEntity deleteCartItem(@PathVariable("cartItemId") Long cartItemId, Principal principal) {


        /* 로그인된 회원과 장바구니 상품에 저장되어 있는 회원이 같은지 체크 로직 */
        if(!cartService.validateCartItem(cartItemId, principal.getName()))
            return new ResponseEntity<String>("수정할 수 있는 권한이 없습니다.", HttpStatus.FORBIDDEN);


        /* 체크 로직이 성공적으로 수행 되었다면 상품을 삭제함. */
        cartService.deleteCartItem(cartItemId);
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    @PostMapping("/cart/orders")
    public @ResponseBody ResponseEntity orderCartItem(@RequestBody CartOrderDto cartOrderDto, Principal principal) {

        List<CartOrderDto> cartOrderDtoList = cartOrderDto.getCartOrderDtoList();


        /* 주문할 상품을 선택했는지 체크하는 메소드 */
        if (cartOrderDtoList == null || cartOrderDtoList.size() == 0) {
            return new ResponseEntity<String>("주문할 상품을 선택해주세요.", HttpStatus.BAD_REQUEST);
        }

        /* 주문 권한을 체크하는 메소드 */
        for (CartOrderDto cartOrder : cartOrderDtoList) {
            if (!cartService.validateCartItem(cartOrder.getCartItemId(), principal.getName())) {
                return new ResponseEntity<String>("주문 권한이 없습니다.", HttpStatus.FORBIDDEN);
            }


        }
        /* 주문 로직 호출 결과 생성된 주문 번호를 반환 받음. */
        Long orderId = cartService.orderCartItem(cartOrderDtoList, principal.getName());
        /* 생성된 주문 번호와 요청이 성공했따는 HTTP 응답 상태 코드 반환 */
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

}
