package toy.project.repository;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import toy.project.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    /* @Query 안에 들어가는 문법은 JPQL임.
    *  조회 조건이 복잡하지 않으면 QueryDsl을 사용하지 않고 @Query 어노테이션을 이용하는 것도 좋은 방법. */
    @Query("select o from Order o " +
            "where o.member.loginId = :loginId " +
            "order by o.orderDate desc"
    )
    /* 현재 로그인한 사용자의 주문 데이터를 페이징 조건에 맞춰서 조회 */
    List<Order> findOrders(@Param("loginId") String loginId, Pageable pageable);

    @Query("select count(o) from Order o " +
            "where o.member.loginId = :loginId"
    )
    /* 현재 로그인한 회원의 주문 개수가 몇 개인지 조회 */
    Long countOrder(@Param("loginId") String loginId);
}