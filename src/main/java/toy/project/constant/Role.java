package toy.project.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN", "관리자"),
    USER("ROLE_USER", "사용자"), SOCIAL("ROLE_SOCIAL", "소셜 사용자" );  // 일반 유저인지, 관리자인지, 소셜 로그인 유저인지 구분하기 위함

    private final String key;
    private final String title;
}
