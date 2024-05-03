package toy.project.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import toy.project.constant.ItemSellStatus;

import java.time.LocalDateTime;

@Data
public class ItemDto {

    private Long id;

    private String itemName;

    private Integer price;

    private String itemDetail;

    private ItemSellStatus itemSellStatus;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime regTime;

    private LocalDateTime updateTime;
}
