package com.example.desafio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.desafio.models.Debt;

@Repository
public interface DebtRepository extends CrudRepository<Debt, Long>  {
    
    public Optional<Debt> findByUserIdAndId(Long userId, Long id);
    public List<Debt> findByUserId(Long userId);
}
