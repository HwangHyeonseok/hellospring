package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional // JPA는 행의 변화가 있을 때 (INSERT, UPDATE, DELETE) 트랜잭션 안에서 실행이 되어야 한다.
public class MemberService {

    private final MemberRepository memberRepository; // MemberRepository 중에서 MemoryMemberRepository

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원 가입
    public Long join(Member member) {

        // 같은 이름이 있는 중복 회원 X
        Optional<Member> result = memberRepository.findByName(member.getName());
        validateDuplicateMember(result);
        memberRepository.save(member);
        return member.getId();
    }

    // 중복 회원 검증
    private static void validateDuplicateMember(Optional<Member> result) {
        result.ifPresent(m -> {
           throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    // 전체 회원 조회
    public List<Member> findMembers() {

        return memberRepository.findAll();
    }

    // ID에 맞는 1명 조회
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
