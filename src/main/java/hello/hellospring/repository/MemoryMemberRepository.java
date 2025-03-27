package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class MemoryMemberRepository implements MemberRepository {
    private static Map<Long, Member> store = new HashMap<>();
    private static Long sequence = 0L;

    // 멤버의 정보를 저장한다. (id는 auto_increment)
    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    // 특정 아이디가 있는 유저의 정보를 1개 리턴
    @Override
    public Optional<Member> findById(Long id) {
        Optional<Member> result = Optional.ofNullable(store.get(id));

        return result;
    }

    // 특정 이름이 있는 모든 유저의 정보를 1개 리턴
    @Override
    public Optional<Member> findByName(String name) {
        Optional<Member> result = store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();

        return result;
    }

    // 모든 유저를 조회
    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    // store을 모두 없애주는 로직
    @Override
    public void clearStore() {
        store.clear();
    }
}
