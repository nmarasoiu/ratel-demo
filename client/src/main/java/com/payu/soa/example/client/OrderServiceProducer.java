package com.payu.soa.example.client;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceProducer {

	public static final int ORDERS_TO_CREATE = 5;

	private final TestBean testBean;

	@Autowired
	public OrderServiceProducer(TestBean testBean) {
		this.testBean = testBean;
	}

	private List<Integer> orderSessionIds = IntStream
			.rangeClosed(0, ORDERS_TO_CREATE).boxed()
			.collect(Collectors.toList());


	@Scheduled(initialDelay = 1000, fixedRate = 5000)
	public void cron() {
		orderSessionIds.parallelStream().forEach(testBean::createOrderInAsync);
	}

}
