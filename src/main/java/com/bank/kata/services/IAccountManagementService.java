package com.bank.kata.services;

import com.bank.kata.entities.Operation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IAccountManagementService {

    Operation makeDeposit(String identifier, String accountNumber, BigDecimal amount);

    Operation makeWithdrawal(String identifier, String accountNumber, BigDecimal amount);

    List<Operation> getHistoryOfPeriod(String identifier, String accountNumber, LocalDate startDate, LocalDate endDate);
}
