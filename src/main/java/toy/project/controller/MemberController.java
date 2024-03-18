package toy.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import toy.project.dto.MemberFromDto;
import toy.project.service.MemberService;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/new")
    public String memberForm(Model model) {
        model.addAttribute("memberFromDto", new MemberFromDto());
        return "member/memberForm";
    }

    @GetMapping("/list")
    public String list() {
        return "member/list";
    }
}
