package com.example.desafio.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.desafio.models.Payment;

public interface PaymentRepository extends CrudRepository<Payment, Long> {
    
}
