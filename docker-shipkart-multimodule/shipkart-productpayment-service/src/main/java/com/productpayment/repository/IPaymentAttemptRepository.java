package com.productpayment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.productpayment.model.entities.PaymentAttempt;

@Repository
public interface IPaymentAttemptRepository extends JpaRepository<PaymentAttempt, Integer> {

	List<PaymentAttempt> findByPayment_PaymentId(Integer paymentId);

}
