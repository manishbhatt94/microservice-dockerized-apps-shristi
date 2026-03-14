package com.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements IInventoryService {

	@Value("${order.topic-name}")
	private String topicName;

	private Logger logger = LoggerFactory.getLogger(InventoryServiceImpl.class);

	private String messageStatus;

	@Override
	public String getStatus() {
		return messageStatus;
	}

	@KafkaListener(topics = { "order-topic" }, groupId = "order_group_id")
	public void consumeMessage(String messageReceived) {
		logger.info("consumeMessage - received: {}", messageReceived);
		messageStatus = messageReceived;
	}

}
