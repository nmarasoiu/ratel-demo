package com.payu.transaction.server.service;

import java.util.Collection;

import com.payu.transaction.event.TransactionChangedEvent;
import com.payu.transaction.event.TransactionStatus;
import com.payu.transaction.server.model.Transaction;

/**
 * Main service for authroization domain 
 *
 */
public interface TransactionService {

	/**
	 * Performs authorization for a given transaction and persists the transaction. 
	 * This method triggers an authorization process on acquirer side. 
	 * After  the transaction is fully confirmed by an acquirer, {@link TransactionChangedEvent} event will be published.
	 * @param transaction the transaction to authorize
	 * @return the id of newly created transaction or <code>null</code> if it was unsuccessful
	 */
	Long authorize(Transaction transaction);
	
	/**
	 * Gets a status of a transaction with a given key. The output of this method should not be cached.
	 * @param transactionId the id of a transaction 
	 * @return the status of this transaction or NoSuchTransactionException if it does not exist.	
	 */
	TransactionStatus getTransactionstStatus(long transactionId);
	
	
	/** 
	 * Finds a given transaction by its primary key. Output of this method should not be cached.
	 * @param id the id of transaction to find.
	 * @return the transaction, or <code>null</code> if such a transaction does not exist.
	 */
	Transaction getTransactionById(Long id);

	/**
	 * Deletes all transactions within a single shard.
	 * @return the number of deleted transactions
	 */
	int deletTransactions();

	
	/**
	 * Gets all transactions bound to a given order within a single data shard. 
	 * Output of this method should not be cached.
	 * @param orderId the id of the order
	 * @return list of transactions for a given order ordered by a creation date.
	 * @throws unexpected exceptions. 
	 */
	Collection<Transaction> getTransactionByOrderId(long orderId);

}
