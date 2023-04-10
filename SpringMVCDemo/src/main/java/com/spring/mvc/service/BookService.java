package com.spring.mvc.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.mvc.entity.Book;


@Service
public class BookService {

	@Autowired
	private com.spring.mvc.database.BookRepository bookRepository;
	
	public Book addBook(Book book) {
		return bookRepository.save(book);
	}
	
	public Book updateBook(Book book) {
		return bookRepository.save(book);
	}
	
	public Book getBook(int bookId) {
		return bookRepository.findById(bookId);
	}
	
	@Transactional
	public void deleteBook(int bookId) {
		System.out.println("Deletingbook!!");
		bookRepository.deleteLikedBook(bookId);
		bookRepository.deleteWatchLaterBook(bookId);
		bookRepository.deleteById(bookId);
	}

	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	public String getFilteredBooks(String searchBy, String searchValue) {
		System.out.println(searchBy+" "+searchValue);
		if(searchBy == null || searchValue == null)
			return null;
		
		List<Book> books = bookRepository.findAll();
		if(searchBy.equals("title"))
			return books.stream().filter(e -> e.getTitle().toLowerCase().contains(searchValue.toLowerCase()) ).collect(Collectors.toList()).toString();
		if(searchBy.equals("bookId"))
			return books.stream().filter(e -> (""+e.getBookId()).contains(searchValue)).collect(Collectors.toList()).toString() ;
		 if(searchBy.equals("price")){
		 		String range[] = searchValue.split("-");
		 		float low = Float.parseFloat(range[0]), high = Float.parseFloat(range[1]);
		 		return books.stream().filter(e -> (low <= e.getPrice() && high >= e.getPrice()) ).collect(Collectors.toList()).toString();
		 }
		 if(searchBy.equals("publisher")){
		 		return books.stream().filter(e -> e.getPublisher().toLowerCase().contains(searchValue.toLowerCase())).collect(Collectors.toList()).toString();
		 }
		 
		
		return null;
	}

	
	
	
}
