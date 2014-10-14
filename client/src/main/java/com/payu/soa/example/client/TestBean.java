package com.payu.soa.example.client;

import org.springframework.remoting.RemoteConnectFailureException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.payu.discovery.Cachable;
import com.payu.discovery.Discover;
import com.payu.discovery.RetryPolicy;
import com.payu.order.server.model.Order;
import com.payu.order.server.service.OrderService;
import com.payu.transaction.server.service.TransactionService;
import com.payu.user.server.model.User;
import com.payu.user.server.service.NoSuchUserException;
import com.payu.user.server.service.PosService;
import com.payu.user.server.service.UserService;

@Component
public class TestBean {

	@Discover
	@RetryPolicy(exception = RemoteConnectFailureException.class)
	private OrderService orderService;

	@Discover
	@Cachable
	private PosService posService;

	@Discover
	@Cachable
	private UserService userService;

	@Discover
	@RetryPolicy(exception = RemoteConnectFailureException.class)
	private TransactionService transService;

	@Async
	public void createOrderInAsync(Order order) {
		Long userId = userService.createUser(new User("Jan Kowalski", "jan.kowalski@gmail.com", "74030200430"));
		try {
			userService.activateUser(userId);
		} catch (NoSuchUserException e) {
			e.printStackTrace();
		}
		orderService.createOrder(order);

	}

}
