package com.payu.transaction.event;

/**
 * possible statuses of transaction in a transaction workflow.
 *
 */
public enum TransactionStatus {
	/** Transaction is confirmed by an acquirer */
	AUTHORIZED,
	
	/**
	 * Transaction authorization has been started. The acquirer has not yet confirmed it.
	 */
	SENT
}
