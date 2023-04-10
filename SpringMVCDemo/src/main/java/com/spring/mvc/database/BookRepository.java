package com.spring.mvc.database;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

import com.spring.mvc.entity.Book;

@Repository
public class BookRepository {

	@Autowired
	private SessionFactory factory;

	public void deleteLikedBook(int bookId) {
		Session session = factory.openSession();

		session.getTransaction().begin();
		session.createQuery("delete from Book_details_liked_books where liked_books_book_id = ?1").executeUpdate();

		session.getTransaction().commit();
		session.close();
	}

	public void deleteWatchLaterBook(int bookId) {
		Session session = factory.openSession();

		session.getTransaction().begin();
		session.createQuery("delete from Book_details_wish_list where wish_list_book_id = ?1").executeUpdate();

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

		session.createQuery("delete Book from Book Book where Book.id=" + BookId).executeUpdate();

		session.getTransaction().commit();
		session.close();
	}
}
