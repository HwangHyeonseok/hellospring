package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest // 스프링 컨테이너와 테스트를 함께 실행한다.
@Transactional // 테스트 케이스에 붙었을 때 테스트 시작 전에 트랜잭션을 시작하고, test가 끝나면 rollback을 해준다.
class MemberServiceIntegrationTest {
    @Autowired MemberRepository memberRepository;
    @Autowired MemberService memberService;


    // 회원가입을 시키고 나서 잘 해당 유저가 추가가 되었는지 체크
    @Test
    void 회원가입() throws Exception {     // 테스트 코드는 한글로 작성 가능하다!
        // given : 이 데이터를 기반으로~
        Member member1 = new Member();
        member1.setName("hello");

        // when : ~ 메서드를 실행했을 때 잘 실행되는지 본다.
        Long result = memberService.join(member1);

        // then : 다시 조회하여 잘 저장이 되었는지 체크한다.
        Member findMember = memberRepository.findById(result).get();
        Assertions.assertThat(findMember.getName()).isEqualTo(member1.getName()); // 저장된 멤버에 "hello" 라는 사람이 있는지 찾는다.
    }

    // 중복 회원을 잘 걸러내는지 체크 (같은 회원 만들고 오류 터지는지 체크)
    @Test
    public void 중복_회원_예외() {
        // given - member1은 기존회원 -> member1의 중복 가입을 잘 잡는지 체크
        Member member1 = new Member();
        member1.setName("spring");
        Long result = memberService.join(member1);
        Member member2 = new Member();
        member1.setName("spring");

        // when
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member1));// memberService.join(member2) 구문을 실행할 때 IllegalStateException 오류가 터지면 e에 메시지가 담긴다.
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다."); // 오류 메시지 검증


    }

    // 전체 회원 조회 체크 (2개 정도 넣어보고 2개가 나오는지 테스트)
    @Test
    void findMembers() {
        // given
        Member member1 = new Member();
        member1.setName("spring1");
        memberRepository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        memberRepository.save(member2);

        // when
        List<Member> result = memberService.findMembers();

        // then
        Assertions.assertThat(result.size()).isEqualTo(2);
    }

    // ID에 맞는 1명 조회 (2개 정도 넣어보고 그 특정 ID에 맞는 사람이 조회되는지 검증)
    @Test
    void findOne() {
        // given
        Member member1 = new Member();
        member1.setName("spring1");
        memberRepository.save(member1);
        Member member2 = new Member();
        member2.setName("spring2");
        memberRepository.save(member2);

        // when
        Member result = memberService.findOne(memberRepository.findByName("spring1").get().getId()).get();

        // then
        Assertions.assertThat(result.getName()).isEqualTo(member1.getName());
    }
}