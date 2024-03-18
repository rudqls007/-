package toy.project.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import toy.project.constant.Role;
import toy.project.dto.MemberFromDto;

/*
* 회원 정보를 저장하는 Member 엔티티
* 관리할 회원 정보
* 이름
* 아이디
* 비밀번호
* 이메일
* 주소
* 역할 */
@Entity
@Table(name = "member")
@Data
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /* ID 통해 회원을 구분하기 위해 동일한 값이 DB에 저장될 수 없도록 unique 속성을 지정함. */
    @Column(unique = true)
    private String loginId;

    private String name;

    private String password;

    private String email;

    private String address;

    /* 자바의 enum 타입을 엔티티의 속성으로 지정함
     *  Enum을 사용할 때 기본적으로 순서가 저장되는데 enum의 순서가 바뀔 경우 문제가 발생할 수 있으므로 String 타입으로 저장하기 위함 */
    @Enumerated(EnumType.STRING)
    private Role role;

    /* Member 엔티티를 생성하는 메소드 회원가입 메소드를 만들어서 관리를 한다면 코드가 변경되더라도 한 군데만 수정하면 되는 이점이 있음. */
    public static Member createMember(MemberFromDto memberFromDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setLoginId(memberFromDto.getLoginId());
        member.setName(memberFromDto.getName());
        member.setEmail(memberFromDto.getEmail());
        member.setAddress(memberFromDto.getAddress());
        /* 스프링 시큐리티 설정 클래스에 등록한 BcrypPasswordEncoder를 파라미터로 넘겨서 비밀번호를 암호화 함. */
        String password = passwordEncoder.encode(memberFromDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.USER);

        return member;
    }

}
