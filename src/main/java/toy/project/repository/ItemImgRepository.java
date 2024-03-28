package toy.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.project.entity.ItemImg;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {
}
