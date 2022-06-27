package com.example.desafio.controllers;

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

    @PatchMapping(value = "/{id}", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    }, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<Bank> PATCHIndex(
            HttpServletRequest request,
            @PathVariable("id") Long id,
            @RequestBody PATCHIndexBody content) {

        PATCHIndexValidate(content);

        var bank = this.bankService.findBankById(id);
        bank.setName(content.getName());
        this.bankService.save(bank);

        return new ResponseEntity<>(bank, HttpStatus.OK);
    }
}
