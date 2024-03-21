package toy.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import toy.project.dto.MemberFormDto;
import toy.project.dto.UserRequestDto;
import toy.project.entity.Member;
import toy.project.repository.MemberRepository;

import java.util.HashMap;
import java.util.Map;

@Service
/**
 * 비즈니스 로직을 담당하는 서비스 계층 클레스에 @Transactional 어노테이션을 선언하여,
 * 로직을 처리하다 에러가 발생했다면 변경된 데이터 로직을 수행하기 이전 상태로 콜백 시켜줌.
 * */
@Transactional
/**
 * 빈을 주입하는 방법으로는,
 * 1. @Autowired 어노테이션 이용
 * 2. 필드 주입(Setter 주입)
 * 3. 생성자 주입
 * 세 가지 방법이 있음.
 * @RequiredArgsConstructor 어노테이션은 final이나 @NotNull이 붙은 필드에 생성자를 자동으로 생성해 줌.
 * 빈에 생성자가 1개일 때만 @Autowired 없이 의존성 주입이 가능함.
 */
@RequiredArgsConstructor
@Slf4j
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByLoginId(member.getLoginId());
        if (findMember != null) {
            throw new IllegalArgumentException("이미 가입된 회원입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        Member member = memberRepository.findByLoginId(loginId);

        if (member == null) {
            throw new UsernameNotFoundException(loginId);
        }
        /* UserDetail을 구현하고 있는 User 객체를 반환해줌.
        *  User 객체를 생성하기 위해서 생성자로 회원의 아이디, 비밀번호, role을 파라미터로 넘겨줌.*/
        return User.builder()
                .username(member.getLoginId())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

    /* 회원 가입 시, 유효성 체크 */

    /**
     * 유효성 검사에 실패한 필드들은 Map 자료구조를 통해 키값과 에러 메시지를 응답한다.
     * Key : valid_{dto 필드명}
     * Message : dto에서 작성한 message 값
     * 유효성 검사에 실패한 필드 목록을 받아 미리 정의된 메시지를 가져와 Map에 넣어준다.
     */
    @Transactional(readOnly = true)
    public Map<String, String> validateHandling(Errors errors) {
        HashMap<String, String> validatorResult = new HashMap<>();

        /* 유효성 검사에 실패한 필드 목록을 받음 */
        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }

        return validatorResult;
    }


    @Transactional(readOnly = true)
    public void checkLoginIdDuplication(MemberFormDto dto) {
        boolean loginIdDuplicate = memberRepository.existsByLoginId(dto.getLoginId());
        if (loginIdDuplicate) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

    }

    @Transactional(readOnly = true)
    public void checkEmailDuplication(MemberFormDto dto) {
        boolean emailDuplicate = memberRepository.existsByEmail(dto.getEmail());
        if (emailDuplicate) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

    }

}

