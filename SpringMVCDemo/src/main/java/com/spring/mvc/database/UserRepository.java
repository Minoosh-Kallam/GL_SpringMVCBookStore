package com.spring.mvc.database;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.mvc.entity.User;


@Repository
public class UserRepository {
	
	@Autowired
	private SessionFactory factory;

	public List<User> findAll() {
		List<User> Users = new ArrayList<>();
		Session session = factory.openSession();
		
		session.getTransaction().begin();
		Users = session.createQuery("select User from User user", User.class).getResultList();
		
		session.getTransaction().commit();
		session.close();
		
		return Users;
	}
	
	public User findById(int userId) {
		User User ;
		Session session = factory.openSession();
		session.getTransaction().begin();
		
		User = session.createQuery("select user from User user where user.userId="+userId, User.class).getSingleResult();
		 
		session.getTransaction().commit();
		session.close();
		
		return User;
	}
	
	public User save(User user) {
		Session session = factory.openSession();
		session.getTransaction().begin();
		
		session.save(user);
		 
		session.getTransaction().commit();
		session.close();
		return user;
	}
	
	public void deleteById(int userId) {
		Session session = factory.openSession();
		session.getTransaction().begin();
		
		session.createQuery("delete from User user where user.userId="+userId).executeUpdate();
		 
		session.getTransaction().commit();
		session.close();
	}
	
	public User findByUserName(String username) {
		Session session = factory.openSession();
		session.getTransaction().begin();
		
		User user = session.createQuery("select user from User user where user.userName='"+username+"'", User.class).getSingleResult();
		 
		session.getTransaction().commit();
		session.close();
		
		return user;
	}

}
