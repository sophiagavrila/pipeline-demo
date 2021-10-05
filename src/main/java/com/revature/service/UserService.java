package com.revature.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.exceptions.UserNotFoundException;
import com.revature.model.Address;
import com.revature.model.User;
import com.revature.repositories.AddressDAO;
import com.revature.repositories.UserDAO;

@Service
public class UserService {

	/**
	 * @Transactional on Service methods:
	 * 				  In the case that multiple dao methods were
	 *                being called, we want to make sure that those methods are
	 *                fired against the DB in one unit of work (transaction).
	 */

	// SLFj is a logging abstraction for Spring
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private AddressDAO addressDAO;

	@Transactional(readOnly=true)
	public Set<User> findAll() {
		return userDAO.findAll().stream().collect(Collectors.toSet());
	}

	@Transactional(readOnly=true)
	public User findByUsername(String username) {
		return userDAO.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException("No user found with username " + username));
	}

	@Transactional(readOnly=true)
	public User findById(int id) {

		return userDAO.findById(id);
	}
	
	// we want to make sure that this method may only READ from the DB
	@Transactional(readOnly=true)
	public User getById(int id) {
		return userDAO.getById(id);
	}

	/**
	 * Every time that this method is invoked, I want to begin a new transaction
	 * @param User with no ID
	 * @return User with auto-generated ID
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public User insert(User u) {

		if (u.getAddresses() != null) {
			u.getAddresses().forEach(a -> addressDAO.save(a));
		}

		return userDAO.save(u);
	}
	
	@Transactional(propagation=Propagation.REQUIRED) // this is the default setting for all transactional annotations.
	public void remove(int id) {
		try {
			userDAO.deleteById(id);
		} catch (IllegalArgumentException e) {
			logger.warn("id can't be null to deleteById()");
		}
	}

}
