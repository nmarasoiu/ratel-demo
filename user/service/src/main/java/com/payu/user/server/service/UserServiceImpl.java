package com.payu.user.server.service;

import com.payu.discovery.Publish;
import com.payu.training.database.GenericDatabase;
import com.payu.user.server.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Publish
public class UserServiceImpl implements UserService {


    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private GenericDatabase<User> database;


    public Long createUser(User user) {
    	log.info("Real User service call : create User {}", user);
        return database.create(user);
    }

    public User getUserById(Long id) {
        log.info("Real User service call getById {}", id);
        return database.get(id);
    }

    @Override
    public int deleteUsers() {
        log.info("Real User service call deleteUsers ");
        return database.clear();
    }

    public void activateUser(long userId) throws NoSuchUserException {
        User user = database.get(userId);
        if (user != null) {
			user.setActive(true);
			database.update(user);
		} else {
			throw new NoSuchUserException("Could not find user with id " + userId);
		}

    }

}
