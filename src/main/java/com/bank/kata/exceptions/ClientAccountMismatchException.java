package com.bank.kata.exceptions;

public class ClientAccountMismatchException extends RuntimeException{

    public ClientAccountMismatchException(String msg){
        super(msg);
    }
}
