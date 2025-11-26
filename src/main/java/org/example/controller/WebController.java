package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

	@GetMapping("/")
	public String index(Model model) {
		return "index";
	}

	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}

	@GetMapping("/logout")
	public String logout(Model model) {
		return "logout";
	}

}
