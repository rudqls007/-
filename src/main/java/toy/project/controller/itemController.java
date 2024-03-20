package toy.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class itemController {

    @GetMapping("/admin/item/new")
    public String itemForm() {
        return "/item/itemForm";
    }
}
