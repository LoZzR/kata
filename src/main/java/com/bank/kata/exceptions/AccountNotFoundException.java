package com.bank.kata.exceptions;

public class AccountNotFoundException extends RuntimeException{

    public AccountNotFoundException(String msg){
        super(msg);
    }
}