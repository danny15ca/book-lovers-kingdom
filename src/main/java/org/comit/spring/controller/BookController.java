package org.comit.spring.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.comit.spring.bean.Book;
import org.comit.spring.bean.Comment;
import org.comit.spring.bean.User;
import org.comit.spring.service.BookService;
import org.comit.spring.service.CommentService;
import org.comit.spring.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BookController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static String BOOK_IMG_UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads/bookImages";
	public static String BOOK_IMG_RESOURCES_STATIC_DIRECTORY = System.getProperty("user.dir") 
																+ "/src/main/resources/static/bookImages";
	public static String BOOK_FILE_UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads/books";
	
	@Autowired
	BookService bookService;
	
	@Autowired
	CommentService commentService;
	
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
	
	@GetMapping("/")
	String index(Model model,
			@RequestParam("page") Optional<Integer> page, 
			@RequestParam("size") Optional<Integer> size) {
		int currentPage = page.orElse(1);
        int pageSize = size.orElse(3);
        
        Page<Book> bookPage = bookService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        
        model.addAttribute("bookPage", bookPage);

        int totalPages = bookPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
		
		return "index";
	}
	
	@GetMapping("/book/search/{searchText}")
	String showBookInfo(
					@PathVariable String searchText,
					Model model,
					@RequestParam("page") Optional<Integer> page, 
					@RequestParam("size") Optional<Integer> size) {
		int currentPage = page.orElse(1);
        int pageSize = size.orElse(3);
        
        Page<Book> bookPage = bookService.findPaginatedFiltered(searchText, PageRequest.of(currentPage - 1, pageSize));
        
        model.addAttribute("bookPage", bookPage);

        int totalPages = bookPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

		return "search";
	}
	
	@GetMapping("/book/{id}")
	String showBookInfo(
					@PathVariable int id,
					Model model,
					@RequestParam("page") Optional<Integer> page, 
					@RequestParam("size") Optional<Integer> size) {
		Book book = bookService.findBook(id);
		model.addAttribute("book", book);
		
		int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        
        Page<Comment> commentPage = commentService.findPaginated(id, PageRequest.of(currentPage - 1, pageSize));
        
        model.addAttribute("commentPage", commentPage);

        int totalPages = commentPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

		return "book_info";
	}
	
	@GetMapping("/book/review/{id}")
	public String showReview(
					@PathVariable int id,
					Comment comment) {
		Book book = bookService.findBook(id);
		comment.setBook(book);
		
		return "review";
	}
	
	@PostMapping("/book/review/{id}")
	public String writeReview(
					@PathVariable int id,
					Model model,
					Comment comment,
					RedirectAttributes ra) {
		Book book = bookService.findBook(id);
		comment.setBook(book);
		
		String username = (String) model.getAttribute("authUser");
		User user = userService.findUser(username);
		comment.setUser(user);
		model.addAttribute("comment", comment);
		
		commentService.createComment(comment);
		
		ra.addFlashAttribute("operation", "create");
		
		return "redirect:/book/" + id;
	}
	
	@GetMapping("/admin")
	ModelAndView listUsers() {
		List<Book> books = this.bookService.listBooks();
		
		return new ModelAndView("admin","books",books);
	}
	
	@GetMapping("/admin/add-book")
	String showAddBook(Book book) {
		return "add_book";
	}
	
	@PostMapping("/admin/add-book")
	String addBook(Book book,
					@RequestParam("bookImage") MultipartFile imgFile,
					@RequestParam("bookFilePath") MultipartFile eBookFile,
					@RequestParam("coverImgPath") String coverImgPath,
					@RequestParam("eBookPath") String eBookPath,
					RedirectAttributes ra) throws IOException {
		
		this.logger.debug("Creating New Book, {}", book.toString());
		
		/*
		 * Upload the Book's Cover Image to 'uploads' folder on the Server
		 */
		Path bookCoverUploadDestinationFile = Paths.get(BOOK_IMG_UPLOAD_DIRECTORY, imgFile.getOriginalFilename());
		Files.write(bookCoverUploadDestinationFile, imgFile.getBytes());
		
		if (!imgFile.isEmpty()) {
			book.setCoverImgPath(imgFile.getOriginalFilename());
		} else {
			book.setCoverImgPath(coverImgPath);
		}
		
		/*
		 * Upload the Book's Cover Image to 'static' resources folder on the Server
		 */
		Path bookCoverImgStaticResourcesFile = Paths.get(BOOK_IMG_RESOURCES_STATIC_DIRECTORY, imgFile.getOriginalFilename());
		Files.write(bookCoverImgStaticResourcesFile, imgFile.getBytes());
		
		/*
		 * Upload the Book's File to 'uploads' folder on the Server
		 */
		Path bookFileUploadDestinationFile = Paths.get(BOOK_FILE_UPLOAD_DIRECTORY, eBookFile.getOriginalFilename());
		Files.write(bookFileUploadDestinationFile, eBookFile.getBytes());
		
		if (!eBookFile.isEmpty()) {
			book.seteBookPath(eBookFile.getOriginalFilename());
		} else {
			book.seteBookPath(eBookPath);
		}
		
		this.bookService.addNewBook(book);
		
		ra.addFlashAttribute("operation", "create");
		
		return "redirect:/admin";
		
	}
	
	@PostMapping("/admin/update-book")
	String updateBook(Book book, BindingResult binding,
			@RequestParam("bookImage") MultipartFile imgFile,
			@RequestParam("coverImgPath") String coverImgPath,
			RedirectAttributes ra) throws IOException {
		
		this.logger.debug("Updating Book, {}", book.toString());
		
		if (binding.hasErrors()) {
			return "update_book";
		}
		
		if (!imgFile.isEmpty()) {
			/*
			 * Delete the existing Book's Cover Image on the 'uploads' folder on the Server, if any
			 */
			Path existingCoverImageUpload = Paths.get(BOOK_IMG_UPLOAD_DIRECTORY, book.getCoverImgPath());
			if (Files.exists(existingCoverImageUpload, LinkOption.NOFOLLOW_LINKS)) {
				Files.delete(existingCoverImageUpload);
			}
			
			/*
			 * Delete the existing Book's Cover Image on the 'static' folder on the Server, if any
			 */
			Path existingCoverImageStatic = Paths.get(BOOK_IMG_RESOURCES_STATIC_DIRECTORY, book.getCoverImgPath());
			if (Files.exists(existingCoverImageStatic, LinkOption.NOFOLLOW_LINKS)) {
				Files.delete(existingCoverImageStatic);
			}
			
			/*
			 * Upload the Book's Cover Image to 'uploads' folder on the Server
			 */
			Path bookCoverDestinationFile = Paths.get(BOOK_IMG_UPLOAD_DIRECTORY, imgFile.getOriginalFilename());
			Files.write(bookCoverDestinationFile, imgFile.getBytes());
			
			if (!imgFile.isEmpty()) {
				book.setCoverImgPath(imgFile.getOriginalFilename());
			} else {
				book.setCoverImgPath(coverImgPath);
			}
			
			/*
			 * Upload the Book's Cover Image to 'static' resources folder on the Server
			 */
			Path bookCoverImgStaticResourcesFile = Paths.get(BOOK_IMG_RESOURCES_STATIC_DIRECTORY, imgFile.getOriginalFilename());
			Files.write(bookCoverImgStaticResourcesFile, imgFile.getBytes());
		}
		
		this.bookService.updateBook(book);
		
		ra.addFlashAttribute("operation", "update");
		
		return "redirect:/admin";
	}
	
	@GetMapping("/admin/update-book/{id}")
	ModelAndView showUpdate(@PathVariable int id) {
		
		Book book = bookService.findBook(id);
		
		return new ModelAndView("update_book", "book", book);
	}
	
	@GetMapping("/admin/delete-book/{id}")
	String deleteBook(@PathVariable int id, RedirectAttributes ra) throws IOException {
		
		Book book = this.bookService.findBook(id);
		
		this.bookService.deleteBook(id);
		
		/*
		 * Delete the existing Book's Cover Image on the 'uploads' folder on the Server, if any
		 */
		Path existingCoverImageUpload = Paths.get(BOOK_IMG_UPLOAD_DIRECTORY, book.getCoverImgPath());
		if (Files.exists(existingCoverImageUpload, LinkOption.NOFOLLOW_LINKS)) {
			Files.delete(existingCoverImageUpload);
		}
		
		/*
		 * Delete the existing Book's Cover Image on the 'static' folder on the Server, if any
		 */
		Path existingCoverImageStatic = Paths.get(BOOK_IMG_RESOURCES_STATIC_DIRECTORY, book.getCoverImgPath());
		if (Files.exists(existingCoverImageStatic, LinkOption.NOFOLLOW_LINKS)) {
			Files.delete(existingCoverImageStatic);
		}
		
		ra.addFlashAttribute("operation", "delete");
		
		return "redirect:/admin";
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
