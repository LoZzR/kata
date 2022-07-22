package com.bank.kata.services;

import com.bank.kata.KataApplication;
import com.bank.kata.entities.Account;
import com.bank.kata.entities.Client;
import com.bank.kata.entities.Operation;
import com.bank.kata.entities.TypeOperation;
import com.bank.kata.exceptions.ClientAccountMismatchException;
import com.bank.kata.exceptions.InsufficientBalanceException;
import com.bank.kata.repositories.AccountRepository;
import com.bank.kata.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

//The DataSource is created with initial data at resources/data.sql file
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AccountManagementServiceTests {

    @Autowired
    private IAccountManagementService accountManagementService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    void contextLoads() {
        assertNotNull(accountManagementService);
    }

    @Test
    void makeWithdrawalClientAccountMismatch(){
        assertThrows(ClientAccountMismatchException.class, () ->
                this.accountManagementService.makeWithdrawal("CLIENT2",
                        "1111111111", BigDecimal.valueOf(500)));
    }

    @Test
    void makeWithdrawalInusfficientBalance(){
        assertThrows(InsufficientBalanceException.class, () ->
                this.accountManagementService.makeWithdrawal("CLIENT1",
                        "1111111111", BigDecimal.valueOf(5000)));
    }

    @Test
    void makeWithdrawalPositifTest(){
        Account account = this.accountRepository.findAccountByAccountNumber("1111111111");
        Operation newOperation = this.accountManagementService.makeWithdrawal("CLIENT1",
                "1111111111", BigDecimal.valueOf(500));

        assertAll("operation", () ->
        {
            assertNotNull(newOperation);
            assertNotNull(newOperation.getAccount());
            assertEquals("1111111111", newOperation.getAccount().getAccountNumber());
            assertEquals(TypeOperation.WITHDRAWAL, newOperation.getTypeOperation());
            assertNotNull(newOperation.getDateOperation());
            assertEquals(BigDecimal.valueOf(500), newOperation.getAmount());
            assertEquals(account.getBalance().subtract(BigDecimal.valueOf(500)), newOperation.getBalanceAfterOperation());
            assertEquals(newOperation.getAccount().getBalance(), newOperation.getBalanceAfterOperation());
            /*assertNotNull(account.getOperations());
            assertTrue(account.getOperations().contains(newOperation));*/
        });
    }

    @Test
    void makeDepositNegatif(){
        assertThrows(ClientAccountMismatchException.class, () ->
                this.accountManagementService.makeDeposit("333333333", BigDecimal.valueOf(500)));}


    @Test
    void makeDepositPositif(){
        Account account = this.accountRepository.findAccountByAccountNumber("1111111111");
        Operation newOperation = this.accountManagementService.makeDeposit("1111111111", BigDecimal.valueOf(500));

        assertAll("operation", () ->
        {
            assertNotNull(newOperation);
            assertNotNull(newOperation.getAccount());
            assertEquals("1111111111", newOperation.getAccount().getAccountNumber());
            assertEquals(TypeOperation.DEPOSIT, newOperation.getTypeOperation());
            assertNotNull(newOperation.getDateOperation());
            assertEquals(BigDecimal.valueOf(500), newOperation.getAmount());
            assertEquals(account.getBalance().add(BigDecimal.valueOf(500)), newOperation.getBalanceAfterOperation());
            assertEquals(newOperation.getAccount().getBalance(), newOperation.getBalanceAfterOperation());
            /*assertNotNull(account.getOperations());
            assertTrue(account.getOperations().contains(newOperation));*/
        });
    }
}
