package org.comit.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
	
	@GetMapping("/signup")
	String signUpNewUser() {
		return "signup";
	}

}
