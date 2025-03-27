package hello.hellospring.domain;

import jakarta.persistence.*;

@Entity // JPA가 관리하는 entity
public class Member {
    // @Id = PK라는 의미
    // @GeneratedValue = 값을 직접 넣지 않아도 됨(DB가 자동으로 생성해줌 - default 느낌)
    // GenerationType.IDENTITY : DB가 자동으로 생성해주는 방식 중에 auto_increment을 선택
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "username") // DB의 칼럼이 username인 것과 매핑된다.
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Member{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
