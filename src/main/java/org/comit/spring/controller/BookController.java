package org.comit.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookController {

	@GetMapping("/")
	String index() {
		return "index";
	}
	
	@GetMapping("/admin")
	String addNewBook() {
		return "admin";
	}
	
	@GetMapping("/genres")
	String browseGenres() {
		return "genres";
	}
	
	@GetMapping("/search")
	String searchBooks() {
		return "search";
	}
}
