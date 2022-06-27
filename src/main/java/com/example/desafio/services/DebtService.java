package com.example.desafio.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.desafio.models.Debt;
import com.example.desafio.repository.DebtRepository;

@Service
public class DebtService {
    @Autowired
    DebtRepository repository;

    public Debt save(Debt entity) {
        return repository.save(entity);
    }

    public Optional<Debt> findById(Long id) {
        return repository.findById(id);
    }

    public Optional<Debt> findByUserIdAndId(Long userId, Long id) {
        return repository.findByUserIdAndId(userId, id);
    }
}
