package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.domain.MemberForm;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    // 생성자 주입(추천)
    private final MemberService memberService;
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 객체 주입 (비추천)
//    @Autowired private MemberService memberService;

    // setter 주입 (비추천)
//    private MemberService memberService;
//    @Autowired
//    public void setMemberService(MemberService memberService) {
//        this.memberService = memberService;
//    }

    @GetMapping("members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("members/new")
    public String create(MemberForm form) {
//        System.out.println("form = " + form);
//        System.out.println("form.getName() = " + form.getName());
//        System.out.println("form.getClass() = " + form.getClass());
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("members")
    public String list(Model model) {
        System.out.println("model = " + model);
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
//        System.out.println("aftermodel = " + model.asMap());
        return "members/memberList";
    }
}
