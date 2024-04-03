package toy.project.dto;

import lombok.Data;
import toy.project.constant.ItemSellStatus;

import java.time.LocalDateTime;

@Data
public class ItemDto {

    private Long id;

    private String itemName;

    private Integer price;

    private String itemDetail;

    private ItemSellStatus itemSellStatus;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;
}
