package com.bank.kata.services;

import com.bank.kata.entities.Account;
import com.bank.kata.entities.Operation;
import com.bank.kata.entities.TypeOperation;
import com.bank.kata.exceptions.AccountNotFoundException;
import com.bank.kata.exceptions.ClientAccountMismatchException;
import com.bank.kata.exceptions.DateMismatchException;
import com.bank.kata.exceptions.InsufficientBalanceException;
import com.bank.kata.repositories.AccountRepository;
import com.bank.kata.repositories.OperationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountManagementService implements IAccountManagementService{

    private AccountRepository accountRepository;
    private OperationRepository operationRepository;

    public AccountManagementService(AccountRepository accountRepository,
                                    OperationRepository operationRepository){
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }

    @Override
    @Transactional
    public Operation makeDeposit(String accountNumber, BigDecimal amount) {
        Account currentAccount = this.accountRepository.findAccountByAccountNumber(accountNumber);
        if(currentAccount == null)
            throw new AccountNotFoundException("Account number not found : " + accountNumber);
        return makeOperation(currentAccount, amount, TypeOperation.DEPOSIT);
    }

    @Override
    @Transactional
    public Operation makeWithdrawal(String identifier, String accountNumber, BigDecimal amount) {
        Account currentAccount = this.verifyClientAccountCompatibility(identifier, accountNumber);
        if(amount.compareTo(currentAccount.getBalance()) > 0)
            throw new InsufficientBalanceException("Insufficient balance !");
        return makeOperation(currentAccount, amount, TypeOperation.WITHDRAWAL);
    }

    @Override
    public List<Operation> getHistoryOfPeriod(String identifier, String accountNumber, LocalDate startDate, LocalDate endDate) {
        if(endDate.isBefore(startDate))
            throw new DateMismatchException("the actual end date is before the start date !");
        Account currentAccount = this.verifyClientAccountCompatibility(identifier, accountNumber);

        return currentAccount.getOperations();
    }

    private synchronized Operation makeOperation(Account account, BigDecimal amount, TypeOperation typeOperation){
        if(TypeOperation.DEPOSIT == typeOperation)
            account.setBalance(account.getBalance().add(amount));
        else if(TypeOperation.WITHDRAWAL == typeOperation)
            account.setBalance(account.getBalance().subtract(amount));

        Operation newOperation = new Operation();
        newOperation.setTypeOperation(typeOperation);
        newOperation.setDateOperation(LocalDateTime.now());
        newOperation.setAmount(amount);
        newOperation.setBalanceAfterOperation(account.getBalance());
        newOperation.setAccount(account);
        this.operationRepository.save(newOperation);
        return newOperation;
    }

    private Account verifyClientAccountCompatibility(String identifier, String accountNumber){
        Account currentAccount = this.accountRepository.findAccountByAccountNumber(accountNumber);
        if(identifier == null || !identifier.equals(currentAccount.getClient().getIdentifier()))
            throw new ClientAccountMismatchException("Client identifier is not the actual account client identifier");
        return currentAccount;
    }
}
