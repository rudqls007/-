package toy.project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import toy.project.config.auth.PrincipalDetails;
import toy.project.dto.MemberFormDto;
import toy.project.entity.Member;
import toy.project.service.MemberService;

import java.util.Map;


@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    private final PasswordEncoder passwordEncoder;




    @GetMapping("/new")
    public String memberForm(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/memberForm";
    }

    @PostMapping("/new")
    public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){


        if(bindingResult.hasErrors()) {         // 에러가 있다면 회원 가입 페이지로 이동
            return "member/memberForm";
        }

        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);

        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());     // 회원 가입 시 중복 회원 가입 예외가 발생하면 에러 메시지를 뷰로 전달
            return "member/memberForm";
        }

        return "redirect:/";
    }

    @PostMapping("/check/id")
    public @ResponseBody String idCHeck(@RequestParam String loginId) {
        String checkResult = memberService.idCheck(loginId);
        log.info("loginId", loginId);
        return checkResult;

    }


    @GetMapping("/login")
    public String loginMember() {

        return "member/memberLoginForm";
    }


    // !!!! OAuth로 로그인 시 이 방식대로 하면 CastException 발생함
    @GetMapping("/form/loginInfo")
    @ResponseBody
    public String formLoginInfo(Authentication authentication, @AuthenticationPrincipal PrincipalDetails principalDetails){

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        String member = principal.getName();
        System.out.println(member);

        String user1 = principalDetails.getName();
        System.out.println(user1);

        return member.toString();
    }

    @GetMapping("/oauth/loginInfo")
    @ResponseBody
    public String oauthLoginInfo(Authentication authentication, @AuthenticationPrincipal OAuth2User oAuth2UserPrincipal){
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        System.out.println(attributes);
        // PrincipalOauth2UserService의 getAttributes 내용과 같음

        Map<String, Object> attributes1 = oAuth2UserPrincipal.getAttributes();
        // attributes == attributes1

        return attributes.toString();     //세션에 담긴 user가져올 수 있음
    }



    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호가 맞지 않습니다.");
        return "/member/memberLoginForm";
    }
}