package exercise8.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/events";
    }

    @GetMapping("/home")
    public String homeAlternative() {
        return "redirect:/events";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}