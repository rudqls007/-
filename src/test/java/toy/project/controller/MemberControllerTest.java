package toy.project.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import toy.project.dto.MemberFormDto;
import toy.project.entity.Member;
import toy.project.service.MemberService;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberControllerTest {


    @Autowired
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;


    public Member createMember(String loginId, String password) {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setLoginId(loginId);
        memberFormDto.setPassword(password);
        memberFormDto.setName("이경빈");
        memberFormDto.setAddress("서울시 강남구 언주로");
        memberFormDto.setEmail("dlrudqls55@naver.com");
        Member member = Member.createMember(memberFormDto, passwordEncoder);
        return memberService.saveMember(member);

    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void 로그인_성공_테스트() throws Exception {
        String loginId = "rudqls007";
        String password = "qwer1234";
        this.createMember(loginId, password);

    }
}