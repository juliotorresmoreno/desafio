package com.example.desafio.models;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.example.desafio.types.DebtStatus;

@Entity
@Table(name = "debts")
public class Debt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Bank bank;

    @ManyToOne
    private User user;

    private String concept;

    private Long initialBalance;
    private Long currentBalance;
    private Long monthlyPayment;
    private Long feesPaid;
    private Float interestRate;
    private Float slowPayingInterestRate;
    private int dues;

    private Instant lastPayment;
    private int regularPayDay;

    @Enumerated(value = EnumType.STRING)
    private DebtStatus status;

    @CreatedDate
    private Instant createdDate;

    @LastModifiedDate
    private Instant lastModifiedDate;

    public Debt(
            Long id,
            Bank bank,
            User user,
            String concept,
            Long initialBalance,
            Long currentBalance,
            Long monthlyPayment,
            Long feesPaid,
            Instant lastPayment,
            DebtStatus status,
            int regularPayDay,
            int dues) {
        this.id = id;
        this.bank = bank;
        this.user = user;
        this.concept = concept;
        this.initialBalance = initialBalance;
        this.currentBalance = currentBalance;
        this.monthlyPayment = monthlyPayment;
        this.feesPaid = feesPaid;
        this.lastPayment = lastPayment;
        this.status = status;
        this.regularPayDay = regularPayDay;
    }

    public Debt(
            Bank bank,
            User user,
            String concept,
            Long initialBalance,
            Long currentBalance,
            Long monthlyPayment,
            Long feesPaid,
            Instant lastPayment,
            DebtStatus status,
            int regularPayDay,
            int dues) {
        this.bank = bank;
        this.user = user;
        this.concept = concept;
        this.initialBalance = initialBalance;
        this.currentBalance = currentBalance;
        this.monthlyPayment = monthlyPayment;
        this.feesPaid = feesPaid;
        this.lastPayment = lastPayment;
        this.status = status;
        this.regularPayDay = regularPayDay;
    }

    public Debt() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public Long getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(Long initialBalance) {
        this.initialBalance = initialBalance;
    }

    public Long getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Long currentBalance) {
        this.currentBalance = currentBalance;
    }

    public Long getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(Long monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public Long getFeesPaid() {
        return feesPaid;
    }

    public void setFeesPaid(Long feesPaid) {
        this.feesPaid = feesPaid;
    }

    public Instant getLastPayment() {
        return lastPayment;
    }

    public void setLastPayment(Instant lastPayment) {
        this.lastPayment = lastPayment;
    }

    public DebtStatus getStatus() {
        return status;
    }

    public void setStatus(DebtStatus status) {
        this.status = status;
    }

    public int getRegularPayDay() {
        return regularPayDay;
    }

    public void setRegularPayDay(int regularPayDay) {
        this.regularPayDay = regularPayDay;
    }

    public Float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Float interestRate) {
        this.interestRate = interestRate;
    }
    
    public Float getSlowPayingInterestRate() {
        return slowPayingInterestRate;
    }

    public void setSlowPayingInterestRate(Float slowPayingInterestRate) {
        this.slowPayingInterestRate = slowPayingInterestRate;
    }

    public int getDues() {
        return dues;
    }

    public void setDues(int dues) {
        this.dues = dues;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}