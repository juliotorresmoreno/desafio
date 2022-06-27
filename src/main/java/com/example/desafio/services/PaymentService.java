package com.example.desafio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.desafio.repository.PaymentRepository;

@Service
public class PaymentService {
    @Autowired
    PaymentRepository repository;
}
