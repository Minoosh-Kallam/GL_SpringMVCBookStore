package com.spring.mvc.entity;



import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bookId")
	private int bookId;
	
	@Column(unique = true, name="title")
	private String title;
	
	@Column(name="description")
	private String description;	
	
	private String author;
	
	private String publisher;
	
	private float price;
	
	@ManyToMany(mappedBy = "wishList", cascade = CascadeType.REMOVE )
	private List<User> watchLaterUsers;
	
	@ManyToMany(mappedBy = "likedBooks" , cascade = {CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REFRESH})
	private List<User> likedUsers;
	
	public String toString() {
		return """
				{
					"id": #bookId#,
					"title": "#title#",
					"description": "#description#",
					"author": "#author#",
					"price": #price#,
					"publisher": "#publisher#"
				}
				""".replace("#bookId#", ""+bookId)
				   .replace("#title#", title)
				   .replace("#description#", description)
				   .replace("#author#", author)
				   .replace("#price#", ""+price)
				   .replace("#publisher#", publisher == null ? "" : publisher);
				
	}

	public Book(String title, String description, String publisher, String author, float price) {
		super();
		this.title = title;
		this.description = description;
		this.author = author;
		this.publisher = publisher;
		this.price = price;
	}
	
	

}
