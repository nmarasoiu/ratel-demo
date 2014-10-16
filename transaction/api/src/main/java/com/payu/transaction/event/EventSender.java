package com.payu.transaction.event;

public interface EventSender {
	
	public void sendEvent(TransactionChangedEvent event);

}
