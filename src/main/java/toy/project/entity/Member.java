package toy.project.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import toy.project.config.BaseEntity;
import toy.project.constant.Role;
import toy.project.dto.MemberFormDto;

import java.util.Optional;

/*
* 회원 정보를 저장하는 Member 엔티티
* 관리할 회원 정보
* 이름
* 아이디
* 비밀번호
* 이메일
* 주소
* 역할 */
@Builder
@Entity
@Table(name="member")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @ToString
public class Member extends BaseEntity {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /* ID 통해 회원을 구분하기 위해 동일한 값이 DB에 저장될 수 없도록 unique 속성을 지정함. */
    @Column(unique = true)
    private String loginId;

    private String name;

    private String oriPassword;

    private String password;

    private String picture;

    private String email;

    private String zipcode;

    private String streetAddress;

    private String detailAddress;

    private String provider;   // oauth2를 이용할 경우 어떤 플랫폼을 이용하는지

    private String providerId; // oauth2를 이용할 경우 아이디값


    /* 자바의 enum 타입을 엔티티의 속성으로 지정함
     *  Enum을 사용할 때 기본적으로 순서가 저장되는데 enum의 순서가 바뀔 경우 문제가 발생할 수 있으므로 String 타입으로 저장하기 위함 */
    @Enumerated(EnumType.STRING)
    private Role role;



    /* Member 엔티티를 생성하는 메소드 회원가입 메소드를 만들어서 관리를 한다면 코드가 변경되더라도 한 군데만 수정하면 되는 이점이 있음. */
    public static Member createMember(MemberFormDto memberFromDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setLoginId(memberFromDto.getLoginId());
        member.setName(memberFromDto.getName());
        member.setEmail(memberFromDto.getEmail());
        /* 주소 */
        member.setZipcode(memberFromDto.getZipcode());
        member.setStreetAddress(memberFromDto.getStreetAddress());
        member.setDetailAddress(memberFromDto.getDetailAddress());
        member.setOriPassword(memberFromDto.getPassword());
        /* 스프링 시큐리티 설정 클래스에 등록한 BcrypPasswordEncoder를 파라미터로 넘겨서 비밀번호를 암호화 함. */
        String password = passwordEncoder.encode(memberFromDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.ADMIN);

        return member;
    }


    @Builder(builderClassName = "UserDetailRegister", builderMethodName = "userDetailRegister")
    public Member(String name, String password, String email, Role role) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    @Builder(builderClassName = "OAuth2Register", builderMethodName = "oauth2Register")
    public Member(String name, String loginId, String password, String email, Role role, String provider, String providerId) {
        this.name = name;
        this.loginId =loginId;
        this.password = password;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }


    /**
     * 회원수정 메소드
     */
    public void updateUsername(String name) { this.name = name; }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateOriPassword(String oriPassword) {
        this.oriPassword = oriPassword;
    }

    public void updateAddress(String zipcode) { this.zipcode = zipcode; }

    public void updateStreetAddress(String streetAddress) { this.streetAddress = streetAddress; }

    public void updateDetailAddress(String detailAddress) { this.detailAddress = detailAddress; }


}
