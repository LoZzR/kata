package com.bank.kata.entities;

import java.time.LocalDateTime;

public class Operation {

    private Long id;
    private LocalDateTime dateOperation;
    private Double amountOperation;
    private Double balanceOperation;
    private Account account;

    public Operation() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(LocalDateTime dateOperation) {
        this.dateOperation = dateOperation;
    }

    public Double getAmountOperation() {
        return amountOperation;
    }

    public void setAmountOperation(Double amountOperation) {
        this.amountOperation = amountOperation;
    }

    public Double getBalanceOperation() {
        return balanceOperation;
    }

    public void setBalanceOperation(Double balanceOperation) {
        this.balanceOperation = balanceOperation;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", dateOperation=" + dateOperation +
                ", amountOperation=" + amountOperation +
                ", balanceOperation=" + balanceOperation +
                '}';
    }
}
