package com.spring.mvc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.mvc.database.BookRepository;
import com.spring.mvc.entity.Book;
import com.spring.mvc.entity.User;




@Service
public class UserService {


	@Autowired
	private com.spring.mvc.database.UserRepository userRepository;
	@Autowired
	private BookRepository bookRepository;
	
	public User addUser(User user) {
		if(user.getLikedBooks() == null)
			user.setLikedBooks(new ArrayList<>());
		if(user.getWishList() == null)
			user.setWishList(new ArrayList<>());
		return userRepository.save(user);
	}
	
	public User updateUser(User user) {
		
		return userRepository.save(user);
	}
	
	public void deleteUser(int BookId) {
		userRepository.deleteById(BookId);
	}
	
	public User getUser(int userId) {
		return userRepository.findById(userId);
	}
	
	public User areCredentialsValid(String username, String password) {
		System.out.println(username+" "+password);
		User user = userRepository.findByUserName(username);
		if(user != null && user.getPassword().equals(password))
			return user;
		return null;
	}
	
	public boolean isFav(int userId, int bookId) {
		
		User user = userRepository.findById(userId);
		if(user == null)
			return false;
		
		long count = user.getLikedBooks().stream().filter(e -> e.getBookId() == bookId ).count() ;
		
		return count > 0;
	}
	
	public boolean isWatchLater(int userId, int bookId) {
		
		User user = userRepository.findById(userId);
		if(user == null)
			return false;
		long count = user.getWishList().stream().filter(e -> e.getBookId() == bookId ).count() ;
		
		return count > 0;
	}
	
	public void addToFav(int userId, int bookId) {
		User user = userRepository.findById(userId);
		Book book = bookRepository.findById(bookId);
		
		user.getLikedBooks().add(book);
		book.getLikedUsers().add(user);
		
		System.out.println(user.getLikedBooks());
		System.out.println(book.getLikedUsers());
		
		userRepository.save(user);
		bookRepository.save(book);
	}
	public void removeFromFav(int userid, int bookId) {
		bookRepository.undoLike(userid, bookId);
		
	}
	public void addToWatchLater(int userId, int bookId) {
		User user = userRepository.findById(userId) ;
		Book book = bookRepository.findById(bookId);
		
		user.getWishList().add(book);
		book.getWatchLaterUsers().add(user);
		
		userRepository.save(user);
		bookRepository.save(book);
		
	}
	public void removeFromWatchLater(int userId, int bookId) {
		bookRepository.undoWatchLater(userId, bookId);
	}
	
	public List<Book> getLikedBooks(int BookId) {
		User user = userRepository.findById(BookId);
		if(user == null)
			return new ArrayList<>();
		return user.getLikedBooks() ;
		
	}

	public List<Book> getWatchLaterBook(int BookId) {
		User user = userRepository.findById(BookId);
		if(user == null)
			return new ArrayList<>();
		return user.getWishList() ;		
	}
}
