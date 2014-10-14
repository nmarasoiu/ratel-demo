package com.payu.soa.example.client;


import java.math.BigDecimal;

import org.springframework.remoting.RemoteConnectFailureException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.payu.discovery.Cachable;
import com.payu.discovery.Discover;
import com.payu.discovery.RetryPolicy;
import com.payu.order.server.model.Order;
import com.payu.order.server.service.OrderService;
import com.payu.transaction.server.model.Transaction;
import com.payu.transaction.server.service.TransactionService;
import com.payu.user.server.model.Pos;
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
	private UserService userService;

	@Discover
	@RetryPolicy(exception = RemoteConnectFailureException.class)
	private TransactionService transService;

	@Async
	public void createOrderInAsync(int sessionId) {
		String paymentMethodBrand = "visa";
		Long posId = 2L;
		pay(sessionId, paymentMethodBrand, posId);
	}


	private void pay(int sessionId, String paymentMethodBrand, Long posId) {

		verifyPos(sessionId, paymentMethodBrand, posId);
		
		Long userId = createAndActivateUser();
		
		Order order = createOrder(sessionId, userId);
		Long orderId = orderService.createOrder(order);
		BigDecimal amount = order.getAmount();

		
		Long transId = createTransaction(paymentMethodBrand, orderId, amount);
		
		verifyPaymentProcess(transId, userId, orderId, sessionId, amount, paymentMethodBrand, posId);
	}


	private void verifyPaymentProcess(Long transId, Long userId, Long orderId,
			int sessionId, BigDecimal amount, String paymentMethodBrand,
			Long posId) {
		
		Order order = orderService.getOrder(orderId);
		
//		assertThat(order.getId()).isEqualTo(orderId);
//		assertThat(order.getUserId()).isEqualTo(userId);
//		assertThat(order.getAmount()).isEqualTo(amount);
		
		
		
		Transaction trans = transService.getTransactionById(transId);
		
//		assertThat(trans.getOrderId()).isEqualTo(orderId);
//		assertThat(trans.getAmount()).isEqualTo(amount);
		
		
	}


	private Long createTransaction(String paymentMethodBrand, Long orderId,
			BigDecimal amount) {
		Transaction trans  = new Transaction(amount, paymentMethodBrand, orderId); 
		Long transId = transService.createTransaction(trans);
		return transId;
	}


	private Long createAndActivateUser() {
		Long userId = userService.createUser(new User("Jan Kowalski", "jan.kowalski@gmail.com", "74030200430"));
		try {
			userService.activateUser(userId);
		} catch (NoSuchUserException e) {
			e.printStackTrace();
		}
		return userId;
	}


	private void verifyPos(int sessionId, String paymentMethodBrand, Long posId) {
		Pos pos = posService.getPosById(posId);
		if (!pos.isActive() || !pos.isPaymetnMethodBrandActive(paymentMethodBrand)) {
			throw new IllegalStateException(String.format("Payment for % not possible with method %s and pos %s ", sessionId, paymentMethodBrand, pos.getName()));
		}
	}
	
	
    private Order createOrder(int sessionId, Long userId) {
        Order order = new Order(new BigDecimal(Math.random() * 10000L), "Foo " + sessionId);
        order.setUserId(userId);
		return order;
    }


}
