package com.payu.user.server.service;

import com.payu.user.server.model.User;



public interface UserService {

    Long createUser(User user);

    public User getUserById(Long id);

	public abstract int deleteUsers();

	public void activateUser(long userId) throws NoSuchUserException;

}
