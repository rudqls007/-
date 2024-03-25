package toy.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.project.entity.Cart;


public interface CartRepository extends JpaRepository<Cart, Long> {
}
