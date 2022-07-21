package com.bank.kata.repositories;

import com.bank.kata.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findAccountByAccountNumber(String accountNumber);
}
