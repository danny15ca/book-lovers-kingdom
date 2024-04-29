package org.comit.spring.controller;

import java.util.Optional;

import org.comit.spring.bean.User;
import org.comit.spring.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	UserService userService;
	
	@ModelAttribute("authUser")
	String getAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			return authentication.getName();
		}
		
		return "";
	}
	
	@GetMapping("/signup")
	String showCreateUser(User user) {
		return "signup";
	}
	
	@PostMapping("/signup")
	String createUser(User user, BindingResult binding) {
		
		this.logger.debug("Creating User, {}", user.toString());
		
		this.validateUsername(user, binding);
		
		if (binding.hasErrors()) {
			return "signup";
		}
		
		this.userService.createUser(user);
		
		return "redirect:/login";
	}
	
	private void validateUsername(User user, BindingResult binding) {
		
		if (Optional.ofNullable(this.userService.findUser(user)).isPresent()) {
			binding.addError(new FieldError("user","username",user.getUsername(), 
					false, null, null, "* Username already taken."));
		}
	}

}
