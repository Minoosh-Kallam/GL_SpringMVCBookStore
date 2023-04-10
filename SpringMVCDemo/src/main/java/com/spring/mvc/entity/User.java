package com.spring.mvc.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor @NoArgsConstructor 
@Getter @Setter
@Table(name = "User_details")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int userId;
	@Column(nullable = true, unique = true, name = "user_name")
	private String userName;
	@Column(name = "name")
	private  String name;
	
	@Column(nullable = true, unique = true, name = "email")
	private  String email;
	@Column(name="password")
	private  String password;
	@Column(name="role")
	private String role;
	
	@ManyToMany
	private List<Book> likedBooks;
	
	@ManyToMany
	private List<Book> wishList;
	
	
	public User(String UserName, String name, String email, String password, String role) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.userName = UserName;
		this.role = role;
		likedBooks = new ArrayList<>();
		wishList = new ArrayList<>();
	}
	
	public User(String UserName, String password) {
		this.password = password;
		this.userName = UserName;
		likedBooks = new ArrayList<>();
		wishList = new ArrayList<>();
	}
	

	public String toString() {	
		String toString = """
				{
					UserId : "#UserId#",
					name: "#name#",
					email: "#email#",
					password: "#password#",
					UserName: "#UserName#",
					likedUsers: "#likedUsers#",
					wishList: "#wishList#"
				}
				""".replace("#UserId#", ""+UserId)
				.replace("#name#", name != null ? name : "")
				.replace("#email#", email != null ? email : "")
				.replace("#UserName#", userName != null ? userName : "")
				.replace("#password#", password != null ? password : "")
				.replace("#likedUsers#", likedBooks != null ? likedBooks.toString() : "[]")
				.replace("#wishList#", wishList != null ? wishList.toString() : "[]")
				;
		
		return toString;
	}
	
	public void addBookToFav(Book book) {
		this.getLikedBooks().add(book);
		book.getLikedUsers().add(this);
	}
	
	public void removeBookFromFav(Book book) {
		this.getLikedBooks().remove(book);
		book.getLikedUsers().remove(this);
	}

	
}
