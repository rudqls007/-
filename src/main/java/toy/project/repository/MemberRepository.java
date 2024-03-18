package toy.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.project.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    /* 회원 가입 시 중복된 회원이 있는지 검사를 하기 위해 아이디로 회원을 검사하는 쿼리 메소드 */
    Member findByLoginId(String loginId);

}
