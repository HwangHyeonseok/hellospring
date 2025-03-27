package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    // JPAL | select m from Member m where m.name = ?
    @Override
    Optional<Member> findByName(String name);
    // JPAL | select m from Member m where m.name = ? and m.id = ?
//    Optional<Member> findByNameAndId(String name, Long id);
}
