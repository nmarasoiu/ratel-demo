package com.payu.transaction.server.service;

import com.payu.ratel.Discover;
import com.payu.ratel.Publish;
import com.payu.ratel.event.EventCannon;
import com.payu.transaction.event.TransactionChangedEvent;
import com.payu.transaction.event.TransactionStatus;
import com.payu.transaction.server.model.Transaction;
import com.payu.transaction.server.model.TransactionDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Service
@Publish
public class TransactionServiceImpl implements TransactionService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TransactionServiceImpl.class);

	@Autowired
	private TransactionDatabase database;
	
	@Discover
	private EventCannon eventCannon;

	private Queue<Long> queue = new ConcurrentLinkedQueue<>();

	public Long authorize(Transaction transaction) {
		LOGGER.info("Real Transaction service call : create Transaction {}",
                transaction);
		Long trans = database.create(transaction);
		putToAuthorizationQueue(trans);
		return trans;
	}

	private void putToAuthorizationQueue(Long transId) {
		queue.add(transId);
	}

	public Transaction getTransactionById(Long id) {
		LOGGER.info("Real Transaction service call getById {}", id);
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
		int size = queue.size();
        Long transToSendId = null;
        while ( size-- > 0 && (transToSendId = queue.poll()) != null) {
            try {
                LOGGER.info("Sending notification");
                sendTransStatusNotification(transToSendId);
			} catch (Exception e) {
				queue.add(transToSendId);
			}
		}
	}

	private void sendTransStatusNotification(Long transId) {
		Transaction transaction = database.get(transId);
		transaction.setStatus(TransactionStatus.AUTHORIZED);
		database.update(transaction);
        eventCannon.fireEvent(new TransactionChangedEvent(TransactionStatus.AUTHORIZED, transId));
        LOGGER.info("Sent notification for transaction " + transId );
	}

	@Override
	public TransactionStatus getTransactionstStatus(long transactionId) {
		Transaction transaction = database.get(transactionId);
		return transaction.getStatus();
	}
	
	
}
