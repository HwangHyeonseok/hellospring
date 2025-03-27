package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository {

    // 스프링에서 SQL을 실행할 수 있도록 도와주는 도구 -> JdbcTemplate
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateMemberRepository(DataSource dataSource) { // DB와 연결할 수 잇도록 하는 것들이 DataSource 객체에 담겨져 있다.
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate); // SimpleJdbcInsert는 INSERT SQL문을 쉽게 실행할 수 있도록 도와준다.
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id"); // member 테이블에 데이터를 넣을 거고, id라는 컬럼은 auto_increment가 되는 키라고 알려준다.

        Map<String, Object> parameters = new HashMap<>(); // DB에 넣을 데이터를 Map 형태로 만든다.
        parameters.put("name", member.getName()); // name 이라는 컬럼에 member.getName() 값을 넣는다.

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters)); // 데이터를 실제로 DB에 INSERT하고 생성된 ID값을 받아온다.
        member.setId(key.longValue()); // 받아온 id 값을 member 객체에 넣어준다.
        return member; // DB에 잘 저장된 member 객체를 리턴한다.
    }

    // ID로 회원 조회
    @Override
    public Optional<Member> findById(Long id) {
        List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id); // ? 자리에는 3번째 파라미터인 id 값이 들어간다. 조회 결과는 memberRowMapper을 이용하여 Member 객체로 바꾼다.
        return result.stream().findAny(); // 1개를 꺼내서 Optional로 감싸서 리턴
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return result.stream().findAny(); // 리스트나 배열같은 자료를 stream() 형으로 변환하고 스트림에서 요소 하나를 찾아서 Optional로 감싸서 리턴한다.
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

    // DB에서 조회한 결과를 Member 객체로 바꿔주는 역할
    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            // DB에서 가져온 id 값과 name 값을 Member에 넣는다.
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member; // 만들어진 객체 리턴
        };
    }
}
