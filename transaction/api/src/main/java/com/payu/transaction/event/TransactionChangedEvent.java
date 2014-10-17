package com.payu.transaction.event;

import java.io.Serializable;

/**
 * Event that is populated everytime when transaction state is changed.
 *
 */
public class TransactionChangedEvent implements Serializable {
	
	private TransactionStatus transStatus;
	private Long transactionId;

	public TransactionChangedEvent(TransactionStatus transStatus,
			Long transactionId) {
		super();
		this.transStatus = transStatus;
		this.transactionId = transactionId;
	}

	public TransactionStatus getTransStatus() {
		return transStatus;
	}

	public Long getTransactionId() {
		return transactionId;
	}

}
