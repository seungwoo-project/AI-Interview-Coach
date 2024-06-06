package devdays.AI_InterviewService.controller;

import devdays.AI_InterviewService.entity.User;
import devdays.AI_InterviewService.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 로그인 폼
    @GetMapping("/")
    public String login_form(HttpSession session) {
        if(session.getAttribute("userId") == null) return "basic/login";
        else return "redirect:/list";
    }

    // 로그아웃시 세션초기화
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("userId");
        session.removeAttribute("coverLetterId");
//        session.setAttribute("userId", null);
//        session.setAttribute("coverLetterId", null);
        return "redirect:/";
    }

    // 로그인 판별 여부에 따라 목록으로 갈건지 다시 로그인폼으로 가는 기능 구현
    @PostMapping("/list")
    public String login(@RequestParam String userId, @RequestParam String password, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("userId") == null) {
            boolean loginSuccess = userService.login(userId, password);

            if (loginSuccess) {
                redirectAttributes.addFlashAttribute("message", "로그인이 성공하였습니다.");
                session.setAttribute("userId", userId);
                return "redirect:/list";
            } else {
                model.addAttribute("errorMessage", "아이디 또는 비밀번호를 잘못 입력했습니다.");
                return "basic/login";
            }
        } else {
            return "redirect:/list";
        }
    }

    // 회원가입 폼
    @GetMapping("/register")
    public String register_form() {

        return "basic/register";
    }

    // 회원 데이터베이스 삽입 기능 구현
    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        try {
            userService.signup(user);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("registerErrorMessage", e.getMessage());
            return "basic/register";
        }
    }
}
