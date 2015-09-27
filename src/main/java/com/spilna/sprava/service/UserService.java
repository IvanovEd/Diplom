package com.spilna.sprava.service;

import java.util.List;

import com.spilna.sprava.model.UserIn;

/**
 * @author Ivanov Eduard
 * @version 1.0
 */
public interface UserService {
	/** Save the user and his data in the database */
	public void addUser(String token);

	/** Get user list */
	public List<UserIn> getUser(String id);
}