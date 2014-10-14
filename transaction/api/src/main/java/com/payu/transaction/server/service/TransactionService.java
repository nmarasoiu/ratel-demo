package com.payu.transaction.server.service;

import java.util.Collection;

import com.payu.transaction.server.model.Transaction;

public interface TransactionService {


	Long createTransaction(Transaction transaction);
	Transaction getTransactionById(Long id);

	int deletTransactions();

	Collection<Transaction> getTransactionByOrderId(long orderId);

}
