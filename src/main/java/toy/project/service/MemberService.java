package toy.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.project.entity.Member;
import toy.project.repository.MemberRepository;

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
public class MemberService {

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
}
