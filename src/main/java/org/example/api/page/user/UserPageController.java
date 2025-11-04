package org.example.api.page.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserPageController {

    @GetMapping("/signup")
    public String signup(){
        return "user/signup";
    }


}
