package com.payu.order.server.service;

import java.util.Collection;

import com.payu.order.server.model.Order;

public interface OrderService {

    Long createOrder(Order order);

    Order getOrder(Long id);

	int deleteOrderById();
	
	public Collection<Order> getOrdersByUserId(long userId);


}
