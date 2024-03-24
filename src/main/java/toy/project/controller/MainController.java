package toy.project.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import toy.project.entity.Member;

@Controller
public class MainController {

    @GetMapping("/")
    public String main(Model model, @AuthenticationPrincipal Member memberInfo)throws Exception {

        return "index";
    }

}
