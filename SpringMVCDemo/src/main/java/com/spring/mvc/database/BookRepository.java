package com.spring.mvc.database;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

import com.spring.mvc.entity.Book;
import com.spring.mvc.entity.User;

@Repository
public class BookRepository {

	@Autowired
	private SessionFactory factory;

	public void deleteLikedBook(int bookId) {
		Session session = factory.openSession();

		session.getTransaction().begin();
		session.createNativeQuery("delete from user_book_fav where book_id = "+bookId).executeUpdate();

		session.getTransaction().commit();
		session.close();
	}

	public void deleteWatchLaterBook(int bookId) {
		Session session = factory.openSession();

		session.getTransaction().begin();
		session.createNativeQuery("delete from user_book_watchlater where book_id = "+bookId).executeUpdate();

		session.getTransaction().commit();
		session.close();
	}

	public List<Book> findAll() {
		List<Book> books = new ArrayList<>();
		Session session = factory.openSession();

		session.getTransaction().begin();
		books = session.createQuery("select book from Book book", Book.class).getResultList();

		session.getTransaction().commit();
		session.close();

		return books;
	}

	public Book findById(int id) {
		Book Book;
		Session session = factory.openSession();
		session.getTransaction().begin();

		Book = session.createQuery("select Book from Book Book where Book.id=" + id, Book.class).getSingleResult();

		session.getTransaction().commit();
		session.close();

		return Book;
	}

	public Book save(Book book) {
		Session session = factory.openSession();
		session.getTransaction().begin();

		session.saveOrUpdate(book);

		session.getTransaction().commit();
		session.close();

		return book;
	}

	public void deleteById(int BookId) {
		Session session = factory.openSession();
		session.getTransaction().begin();

		session.createNativeQuery("delete from Book where bookId = " + BookId).executeUpdate();
		
		session.getTransaction().commit();
		session.close();
	}
	
	@Transactional
	public void undoLike(Integer userId, Integer bookId) {

		Session session = factory.openSession();
		session.getTransaction().begin();
		
		User user = session.find(User.class, userId);
		Book book = session.find(Book.class, bookId);
		
		user.getLikedBooks().remove(book);
		book.getLikedUsers().remove(user);
		
		session.persist(user);
		session.persist(book);
		
		System.out.println(user);
		System.out.println(book);

		session.getTransaction().commit();
		session.close();
	}
	
	@Transactional
	public void undoWatchLater(Integer userId, Integer bookId) {

		Session session = factory.openSession();
		session.getTransaction().begin();
		
		User user = session.find(User.class, userId);
		Book book = session.find(Book.class, bookId);
		
		user.getWishList().remove(book);
		book.getWatchLaterUsers().remove(user);
		
		session.persist(user);
		session.persist(book);
		
		System.out.println(user);
		System.out.println(book);

		session.getTransaction().commit();
		session.close();
	}
}
