package toy.project.dto;

import lombok.Data;

/* 회원 가입 화면으로부터 넘어오는 가입정보를 담을 dto */
@Data
public class MemberFromDto {

    private String name;

    private String email;

    private String password;

    private String address;
}
