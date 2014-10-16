package com.payu.transaction.server.service;

import java.util.Collection;

import com.payu.transaction.server.model.Transaction;

public interface TransactionService {


	Long authorize(Transaction transaction);
	
	Transaction getTransactionById(Long id);

	int deletTransactions();

	Collection<Transaction> getTransactionByOrderId(long orderId);

}
