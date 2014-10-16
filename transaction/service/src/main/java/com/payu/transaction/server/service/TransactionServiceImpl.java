package com.payu.transaction.server.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.payu.discovery.Publish;
import com.payu.transaction.event.EventSender;
import com.payu.transaction.event.TransactionChangedEvent;
import com.payu.transaction.event.TransactionStatus;
import com.payu.transaction.server.model.Transaction;
import com.payu.transaction.server.model.TransactionDatabase;

@Service
@Publish
public class TransactionServiceImpl implements TransactionService {

	private static final Logger log = LoggerFactory
			.getLogger(TransactionServiceImpl.class);

	@Autowired
	private TransactionDatabase database;
	
//	@Discover
	private EventSender eventSender = new EventSender() {
		
		@Override
		public void sendEvent(TransactionChangedEvent event) {
			System.out.printf("Trans [%6] status update %s", event.getTransactionId(), event.getTransStatus());
		}
	};

	private Queue<Long> queue = new ConcurrentLinkedQueue<>();

	public Long authorize(Transaction transaction) {
		log.info("Real Transaction service call : create Transaction {}",
				transaction);
		Long trans = database.create(transaction);
		putToAuthorizationQueue(trans);
		return trans;
	}

	private void putToAuthorizationQueue(Long transId) {
		queue.add(transId);
	}

	public Transaction getTransactionById(Long id) {
		log.info("Real Transaction service call getById {}", id);
		return database.get(id);
	}

	@Override
	public int deletTransactions() {
		return database.clear();
	}

	@Override
	public Collection<Transaction> getTransactionByOrderId(long orderId) {
		return database.getAllObjects().stream()
				.filter(t -> t.getOrderId() == orderId)
				.collect(Collectors.toCollection(ArrayList::new));
	}
	
	@Scheduled(initialDelay = 1000, fixedRate = 5000)
	public void notifyTransStatus() {
		System.out.println("Sending notification");
		int size = queue.size();
		Long transToSendId = null;
		while ( size-- > 0 && (transToSendId = queue.poll()) != null) {
			sendTransStatusNotification(transToSendId);
		}
	}

	private void sendTransStatusNotification(Long transId) {
		eventSender.sendEvent(new TransactionChangedEvent(TransactionStatus.AUTHORIZED, transId));
	}
	
	
}
