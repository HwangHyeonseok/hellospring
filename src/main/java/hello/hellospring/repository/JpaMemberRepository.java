package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    // EntityManager 주입 받기 (EntityManager은 JPA에서 DB와 연결된 객체)
    private final EntityManager em;
    // DI
    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member); // member 객체를 DB에 저장
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id); // id 값이 일치하는 DB 회원을 조회한다.
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        // JPAL 이라는 JPA용 쿼리 작성
        // Member.class -> 이 쿼리의 결과로 Member 타입의 객체를 만든다. (결과타입.class)
        // Member 클래스에서 모든 값을 조회하는데, Member 객체의 name 값이(m.name) name인(:name) 것만 조회한다.
        // Member 엔티티(테이블)에서 name이 같은 사람 찾기
        List<Member> result = em.createQuery("select m from Member as m where m.name = :name", Member.class)
                .setParameter("name", name) // :name 에 들어갈 것은 name 값이다.
                .getResultList(); // 조건에 맞는 회원들을 리스트(List) 형태로 리턴한다.

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member as m", Member.class)
                .getResultList();
    }

    @Override
    public void clearStore() {

    }
}
