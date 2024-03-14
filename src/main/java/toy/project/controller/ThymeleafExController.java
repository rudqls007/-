package toy.project.controller;

import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import toy.project.dto.ItemDto;
import toy.project.entity.Item;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/thymeleaf")
public class ThymeleafExController {

    @GetMapping("/ex01")
    public String thymeleafEx(Model model) {
        model.addAttribute("data", "흥해라 쇼핑몰 !");
        return "/thymeleafEx/thymeleafEx01";
    }

    @GetMapping("/ex02")
    public String thymeleafExText(Model model) {
        ItemDto itemDto = new ItemDto();
        itemDto.setItemDetail("상품 상세 설명");
        itemDto.setItemName("테스트 상품1");
        itemDto.setPrice(10000);
        itemDto.setRegTime(LocalDateTime.now());

        model.addAttribute("itemDto", itemDto);

        return "thymeleafEx/thymeleafText";
    }

    @GetMapping("/ex03")
    public String thymeleafExEach(Model model) {

        List<ItemDto> itemDtoList = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {

            ItemDto itemDto = new ItemDto();
            itemDto.setItemDetail("상품 상세 설명 " + i);
            itemDto.setItemName("테스트 상품 " + i);
            itemDto.setPrice(10000 + i);
            itemDto.setRegTime(LocalDateTime.now());


            itemDtoList.add(itemDto);
        }

        model.addAttribute("itemDtoList", itemDtoList);

        return "thymeleafEx/thymeleafEach";

    }

    @GetMapping("/ex04")
    public String thymeleafExIfUnless(Model model) {

        List<ItemDto> itemDtoList = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {

            ItemDto itemDto = new ItemDto();
            itemDto.setItemDetail("상품 상세 설명 " + i);
            itemDto.setItemName("테스트 상품 " + i);
            itemDto.setPrice(10000 + i);
            itemDto.setRegTime(LocalDateTime.now());


            itemDtoList.add(itemDto);
        }

        model.addAttribute("itemDtoList", itemDtoList);

        return "thymeleafEx/thymeleafIfUnless";

    }

    @GetMapping("/ex05")
    public String thymeleafExHref() {
        return "thymeleafEx/thymeleafExHref";
    }

    @GetMapping("/ex06")
    public String thymeleafExParam(String param1, String param2, Model model) {

        model.addAttribute("param1", param1);
        model.addAttribute("param2", param2);

        return "thymeleafEx/thymeleafExParam";

    }
}
