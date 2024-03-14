package toy.project.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ItemDto {

    private Long id;

    private String itemName;

    private Integer price;

    private String itemDetail;

    private String sellStatCd;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;
}
