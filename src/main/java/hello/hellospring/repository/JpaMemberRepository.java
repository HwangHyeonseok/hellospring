package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    // EntityManager 주입 받기 (EntityManager은 JPA를 사용할 때 DB의 연결 정보와 데이터를 가지고 있는 객체)
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
        // SELECT * FROM Member WHERE id={id} // {id}는 findById의 매개변수에서 받아온 값
        Member member = em.find(Member.class, id); // 내가 찾고 싶은 엔티티의 종류(자바)는 Member 이다. 거기서 id 값이 일치하는 DB 회원을 조회한다.
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        // JPQL 이라는 JPA용 쿼리 작성
        // Member.class -> 행에 리턴되는 데이터 타입은  Member 이다. (결과타입.class)
        // Member 클래스에서 모든 값을 조회하는데, Member 객체의 name 값이(m.name) name인(:name) 것만 조회한다.
        // Member 엔티티(테이블)에서 name이 같은 사람 찾기
        List<Member> result = em.createQuery("select m from Member as m where m.name = :name", Member.class)
                .setParameter("name", name) // :name 에 들어갈 것은 name 값이다.
                .getResultList(); // 조건에 맞는 회원들을 리스트(List) 형태로 리턴한다.

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        // select m from Member as m : Member 객체 자체를 모두 select 한다.
        // Member.class :  행에 리턴되는 데이터의 타입은 Member 이다.
        List<Member> result = em.createQuery("select m from Member as m", Member.class)
                .getResultList();
        return result;
    }
}
