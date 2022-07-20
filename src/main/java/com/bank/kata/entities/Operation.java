package com.bank.kata.entities;

import com.bank.kata.utils.DateProcessor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "OPERATIONS")
@SequenceGenerator(
        name = "ID_GENERATOR", sequenceName="S_OPERATION",allocationSize=5,initialValue=1
)
public class Operation {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ID_GENERATOR"
    )
    @Column(name = "OPERATION_ID")
    private Long id;

    @DateTimeFormat(pattern = DateProcessor.DATE_FORMAT)

    private LocalDateTime dateOperation;
    private BigDecimal amount;
    private BigDecimal balanceOperation;

    @Enumerated(EnumType.STRING)
    private TypeOperation typeOperation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID", nullable = false)
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

    public BigDecimal getamount() {
        return amount;
    }

    public void setamount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalanceOperation() {
        return balanceOperation;
    }

    public void setBalanceOperation(BigDecimal balanceOperation) {
        this.balanceOperation = balanceOperation;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TypeOperation getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(TypeOperation typeOperation) {
        this.typeOperation = typeOperation;
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
                ", amount=" + amount +
                ", balanceOperation=" + balanceOperation +
                ", typeOperation=" + typeOperation +
                '}';
    }
}
