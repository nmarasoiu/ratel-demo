package com.payu.user.server.service;

import com.payu.user.server.model.User;



/**
 * Access to a single shard of  repository of users. 
 *
 */
public interface UserService {

	/**
	 * Creates a given user
	 * @param user the user to create
	 * @return id  of a newly created user
	 * @throws unexpected exceptions.
	 */
    Long createUser(User user);
    

    /**
     * Gets user by id (really!)
     * @param id the id of a user.
     * @return the user with a given id or <code>null</code>  if no such user was created.
     */
    public User getUserById(Long id);

    /**
     * Delete all users within a single shard.
     * @return  number of deleted users.
     */
	int deleteUsers();

	/**
	 * Activates a user with a given ide
	 * @param userId the id of a user to activate
	 * @throws NoSuchUserException when the user with a given id does not exist.
	 */
	public void activateUser(long userId) throws NoSuchUserException;

}
