package com.example.desafio.controllers;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import com.example.desafio.models.Bank;
import com.example.desafio.responses.ResponseError;
import com.example.desafio.services.BankService;

@RestController
@RequestMapping("/banks")
public class BanksController {

    @Autowired
    BankService bankService;

    @GetMapping("")
    public ResponseEntity<Iterable<Bank>> GETIndex(HttpServletRequest request) {
        var banks = this.bankService.findAll();
        return new ResponseEntity<>(banks, HttpStatus.OK);
    }

    public static class POSTIndexBody {
        String name;

        public POSTIndexBody() {

        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    void POSTIndexValidate(POSTIndexBody content) {

    }

    @PostMapping(value = "", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    }, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<Bank> POSTIndex(
            HttpServletRequest request,
            @RequestBody POSTIndexBody content) {

        POSTIndexValidate(content);

        var bank = new Bank();
        bank.setName(content.getName());
        bank.setCreatedDate(Instant.now());
        bank.setLastModifiedDate(Instant.now());

        this.bankService.save(bank);

        return new ResponseEntity<>(bank, HttpStatus.OK);
    }

    public static class PATCHIndexBody {
        String name;

        public PATCHIndexBody() {

        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    void PATCHIndexValidate(PATCHIndexBody content) {

    }

    interface PATCHIndexResponse {}
    class PATCHIndexOK extends Bank implements PATCHIndexResponse {
        private Long id;
        private String name;
        private Instant createdDate;
        private Instant lastModifiedDate;

        PATCHIndexOK(Bank bank) {
            this.id = bank.getId();
            this.name = bank.getName();
            this.createdDate = bank.getCreatedDate();
            this.lastModifiedDate = bank.getLastModifiedDate();
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Instant getCreatedDate() {
            return createdDate;
        }

        public Instant getLastModifiedDate() {
            return lastModifiedDate;
        }
    } 
    class PATCHIndexError extends ResponseError implements PATCHIndexResponse {}

    @PatchMapping(value = "/{id}", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    }, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<PATCHIndexResponse> PATCHIndex(
            HttpServletRequest request,
            @PathVariable("id") Long id,
            @RequestBody PATCHIndexBody content) {

        PATCHIndexValidate(content);

        var _bank = this.bankService.findById(id);
        if (_bank.isEmpty() || _bank == null) {
            final var response = new PATCHIndexError();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        var bank = _bank.get();
        bank.setName(content.getName());
        bank.setLastModifiedDate(Instant.now());
        this.bankService.save(bank);

        final var response = new PATCHIndexOK(bank);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
