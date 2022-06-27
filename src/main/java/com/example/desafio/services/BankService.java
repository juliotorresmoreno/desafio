package com.example.desafio.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.desafio.models.Bank;
import com.example.desafio.repository.BankRepository;

@Service
public class BankService {

    @Autowired
    private BankRepository bankRepository;

    public Bank save(Bank s) {
        return this.bankRepository.save(s);
    }

    public Iterable<Bank> findAll() {
        return this.bankRepository.findAll();
    }

    public Optional<Bank> findById(Long id) {
        return bankRepository.findById(id);
    }
}
