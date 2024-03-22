package toy.project.dto;

import lombok.Data;
import toy.project.entity.Member;

import java.io.Serializable;

@Data
public class SessionUserDto implements Serializable {
    // SessionUser는 인증된 사용자 정보만 필요하므로 아래 필드만 선언한다.
    private String name;
    private String email;
    private String picture;

    public SessionUserDto(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.picture = member.getPicture();
    }

}