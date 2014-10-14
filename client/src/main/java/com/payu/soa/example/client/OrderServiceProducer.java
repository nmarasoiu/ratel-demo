package com.payu.soa.example.client;

import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;
import com.payu.order.server.model.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class OrderServiceProducer {

	public static final int ORDERS_TO_CREATE = 50;

	private final TestBean testBean;

	@Autowired
	public OrderServiceProducer(TestBean testBean) {
		this.testBean = testBean;
	}

	private List<Integer> orderSessionIds = IntStream
			.rangeClosed(0, ORDERS_TO_CREATE).boxed()
			.collect(Collectors.toList());


	@Scheduled(initialDelay = 1000, fixedRate = 500)
	public void cron() {
		orderSessionIds.parallelStream().forEach(testBean::createOrderInAsync);
	}

}
