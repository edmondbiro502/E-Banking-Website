package com.ebanking.ebanking.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Transaction {
    private int id;
    private int destAccount;
    private int sourceAccount;
    private float destBalance;
    private float sourceBalance;
    private float amount;
    private LocalDateTime localDate;


    @Override
    public String toString() {
        return " destAccount=" + destAccount +
                ", sourceAccount=" + sourceAccount +
                ", destBalance=" + destBalance +
                ", sourceBalance=" + sourceBalance +
                ", amount=" + amount +
                ", localDate=" + localDate.toLocalDate();
    }

    public Transaction(@JsonProperty("destAcc") int destAccount, @JsonProperty("sourceAcc") int sourceAccount, @JsonProperty("destBal") float destBalance,
                       @JsonProperty("sourceBal") float sourceBalance, @JsonProperty("ammount")float amount, @JsonProperty("date") LocalDateTime localDate) {
        this.destAccount = destAccount;
        this.sourceAccount = sourceAccount;
        this.destBalance = destBalance;
        this.sourceBalance = sourceBalance;
        this.amount = amount;
        this.localDate = localDate;
    }

    public int getId() {
        return id;
    }

    public int getDestAccount() {
        return destAccount;
    }

    public int getSourceAccount() {
        return sourceAccount;
    }

    public float getDestBalance() {
        return destBalance;
    }

    public float getSourceBalance() {
        return sourceBalance;
    }

    public float getAmount() {
        return amount;
    }

    public LocalDateTime getLocalDate() {
        return localDate;
    }
}
