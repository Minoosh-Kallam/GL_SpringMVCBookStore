package com.spring.mvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.mvc.database.BookRepository;
import com.spring.mvc.entity.Book;
import com.spring.mvc.entity.User;
import com.spring.mvc.service.BookService;
import com.spring.mvc.service.UserService;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private BookRepository bookRepository;
	
	@GetMapping("logout")
	public String logout(HttpSession session, Model model) {
		session.invalidate();
		return "redirect:login";
	}
	
	@PostMapping("register")
	public String doRegister(User user, HttpSession session) {
		
		userService.addUser(user);
		session.setAttribute("username" , user.getUserName());
		session.setAttribute("isAdmin" , user.getRole().equals("Admin"));
		return "redirect:login";
	}
	
	@RequestMapping(value = "doLogin")
	public String doLogin(User user, HttpSession session) {
		User newUser = userService.areCredentialsValid(user.getUserName(), user.getPassword());
		if(newUser != null) {
			session.setAttribute("username", newUser.getUserName());
			session.setAttribute("userId", newUser.getUserId());
			session.setAttribute("isAdmin", newUser.getRole().equals("Admin"));
			return "redirect:home";
		}
		session.setAttribute("error", "Invalid credentials: Incorrect username or password");
		return "redirect:login" ;
		
	}
	
	@GetMapping("login")
	public String getLoginPage(HttpSession session, Model model) {
		String username = (String)session.getAttribute("username" );
		String error = (String)session.getAttribute("error" );
		if(username != null) {
			model.addAttribute("username", username);
//			model.addAttribute("isAdmin", session.getAttribute("isAdmin"));
		}
		if(error != null) {
			model.addAttribute("error", error);
			session.removeAttribute("error");
		}
		return "login";
	}
	
	@GetMapping("register")
	public String getRegisterPage() {
		return "register";
	}
	
	@GetMapping("home")
	public String getHomePage(HttpSession session, Model model) {
		String username = (String)session.getAttribute("username") ;
		if(username != null)
			model.addAttribute("username", username);
		
		List<Book> books = bookService.getAllBooks();
		model.addAttribute("books", books);
		return "home";
	}
	
	@GetMapping("/profile")
	public String getProfile(HttpSession session, Model model) {
		
		
		String username = (String)session.getAttribute("username"); 
		if(username == null)
			return "redirect:/login";
		
		int userId = (Integer)session.getAttribute("userId");
		User user = userService.getUser(userId);
		boolean isAdmin = user.getRole().equals("Admin") ;
		List<Book> likedBooks = userService.getLikedBooks(userId);
		List<Book> watchLaterBooks = userService.getWatchLaterBook(userId);
		
		model.addAttribute("watchLaterBooks" , watchLaterBooks);
		model.addAttribute("likedBooks", likedBooks);
		model.addAttribute("username", username);
		model.addAttribute("isAdmin", isAdmin);
		
		
		
		return "profile";
	}
	
	@PostConstruct
	public void addData() {
		try {
			User user = new User("Admin", "Admin", "Admin@gmail.com", "Admin18@", "Admin");
			userService.addUser(user);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		List<Book> books = List.of(
				new Book("The Great Gatsby", "A novel by F. Scott Fitzgerald set in the roaring 20s, exploring the decadence and excess of the era.", "Scribner", "F. Scott Fitzgerald",  964.95f ),
				new Book( "1984", "A dystopian novel by George Orwell about a society ruled by a totalitarian government that controls every aspect of citizens' lives.", "Signet Classics", "George Orwell", 759.35f),
				 new Book("To Kill a Mockingbird", "A Pulitzer Prize-winning novel by Harper Lee about racial injustice and loss of innocence in a small Alabama town during the Great Depression.", "Harper Perennial Modern Classics", "Harper Lee", 1138.35f ),
				 new Book("The Catcher in the Rye", "A novel by J.D. Salinger about a teenage boy's experiences in New York City after being expelled from prep school.", "Little, Brown and Company", "J.D. Salinger",  859.05f  ),
				 new Book("The Hobbit", "A fantasy novel by J.R.R. Tolkien about the adventures of hobbit Bilbo Baggins as he journeys to reclaim a treasure stolen by the dragon Smaug.", "Houghton Mifflin Harcourt", "J.R.R. Tolkien", 713.45f) 
		);
		
		try {
			for(Book book: books)
				bookRepository.save(book);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
