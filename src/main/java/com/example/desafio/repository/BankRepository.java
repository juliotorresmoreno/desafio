
package com.example.desafio.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.desafio.models.Bank;

public interface BankRepository extends CrudRepository<Bank, Long> {
    Bank findBankById(Long id);
}
