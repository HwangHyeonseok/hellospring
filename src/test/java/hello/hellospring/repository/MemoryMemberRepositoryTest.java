package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    // 각 test가 끝나고 Member 객체가 중복되지 않도록 하기 위해 afterEach 메서드 사용
    @AfterEach // test를 하나 끝난 후에 자동으로 실행되는 메서드에 붙이는 어노테이션
    public void afterEach() {
        repository.clearStore();
    }

    // MemoryMemberRepository 클래스 안에 있는 save() 메서드 테스트
    // 멤버 이름이 "spring"인 사람이 잘 저장되는지 테스트
    @Test // JUnit이 "이 메서드 테스트니까 실행해줘~" 한다.
    public void save() {
        // member의 이름이 spring 이라고 가정하고 테스트
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();  //  .get 을 사용하면 Optional 껍데기가 벗겨져서 나옴
        assertThat(result).isEqualTo(member);
    }

    // MemoryMemberRepository 클래스 안에 있는 findById() 메서드 테스트
    // repository에 이름이 "spring1"과 "spring2"인 Member을 만들어 저장하고 "spring1"인 객체를 잘 가져오는지(findByName 메서드) 검증
    @Test
    public void findByName() {
        // given (testcase)
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 =  new Member();
        member2.setName("spring2");
        repository.save(member2);

        // when
        Member result = repository.findByName("spring1").get();

        // then
        assertThat(result).isEqualTo(member1);
    }

    // repository에 이름이 "spring1"과 "spring2"인 Member을 만들어 저장하고(given) 모든 객체가 잘 담겼는지(when) 검증(then)
    @Test
    public void findAll() {
        // given
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        // when
        List<Member> result = repository.findAll();

        // then
        Assertions.assertThat(result.size()).isEqualTo(2);
    }
}
