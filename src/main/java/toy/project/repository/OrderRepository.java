package toy.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.project.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
