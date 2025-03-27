package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/") // localhost:8080 접속 시 아래 home() 메서드 호출
    public String home() {
        return "home"; // home.html 실행
    }
}
