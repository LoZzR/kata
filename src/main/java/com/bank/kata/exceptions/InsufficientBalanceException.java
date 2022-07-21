package com.bank.kata.exceptions;

public class InsufficientBalanceException extends RuntimeException{

    public InsufficientBalanceException(String msg){
        super(msg);
    }
}
