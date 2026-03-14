package com.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Value("${order.topic-name}")
	private String topicName;

	Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Override
	public void sendStatus(String message) {
		logger.info("Sending message: {}", message);
		kafkaTemplate.send(topicName, message);
		logger.info("Message sent to Kafka topic: {}", topicName);
	}

}
