package toy.project.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import toy.project.repository.MemberRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("Auditing 테스트")
    @WithMockUser(username = "gildong", roles = "USER")
    public void auditingTest() {
        Member NewMember = new Member();
        memberRepository.save(NewMember);

        em.flush();
        em.clear();
        Member member = memberRepository.findById(NewMember.getId()).orElseThrow(EntityNotFoundException::new);


        System.out.println("register time = " + member.getRegTime());
        System.out.println("update time = " + member.getUpdateTime());
        System.out.println("create member = " + member.getCreateBy());
        System.out.println("modify member = " + member.getModifiedBy());


    }
}