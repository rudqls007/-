package toy.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.project.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
