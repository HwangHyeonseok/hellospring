package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// SpringDataJpaMemberRepository(Spring Data JPA 인터페이스)를 만들어 놓으면 스프링에서 구현체를 자동으로 만들어서 스프링 빈에 자동으로 등록을 해놓는다.
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository { // JpaRepository<엔티티,PK의 자료형>

    // JPQL | select m from Member m where m.name = ?
    @Override
    Optional<Member> findByName(String name);
    // JPQL | select m from Member m where m.name = ? and m.id = ?
//    Optional<Member> findByNameAndId(String name, Long id);
}
