package com.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.model.Product;

@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private KafkaTemplate<String, Product> template;

	@Value("${order.topic-name}")
	private String orderTopicName;

	Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Override
	public void sendStatus(String message) {
		logger.info("Sending message: {}", message);
		kafkaTemplate.send(orderTopicName, message);
		logger.info("Message sent to Kafka topic: {}", orderTopicName);
	}

	@Override
	public String placeOrder(Product product) {
		// send message to KafkaServer
		final String topicName = "product-topic";
		template.send(topicName, product);
		logger.info("Event sent to kafka topic ('{}'). Event sent: {}", topicName, product);
		return "Order placed successfully";
	}

}
