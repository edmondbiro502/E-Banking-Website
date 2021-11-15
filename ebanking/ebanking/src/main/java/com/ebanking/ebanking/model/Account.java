package com.ebanking.ebanking.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {
    private int id;
    private int accountNo;
    private String bank;
    private int clientId;
    private float balance;
    private int state;

    public Account(int accountNo, String bank,
                   int clientId, float balance,
                   int state) {
        this.accountNo = accountNo;
        this.bank = bank;
        this.clientId = clientId;
        this.balance = balance;
        this.state = state;
    }

    public Account(@JsonProperty("id") int id,@JsonProperty("accountNo") int accountNo, @JsonProperty("bank") String bank,
                   @JsonProperty("clientId") int clientId,@JsonProperty("balance") float balance,
                   @JsonProperty("state") int state) {
        this.id = id;
        this.accountNo = accountNo;
        this.bank = bank;
        this.clientId = clientId;
        this.balance = balance;
        this.state = state;
    }

    public Account(int accountNo,int clientId) {
        this.accountNo = accountNo;
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountNo=" + accountNo +
                ", bank='" + bank + '\'' +
                ", clientId=" + clientId +
                ", balance=" + balance +
                ", state=" + state +
                '}';
    }

    public int getAccountNo() {
        return accountNo;
    }

    public String getBank() {
        return bank;
    }

    public int getClientId() {
        return clientId;
    }

    public float getBalance() {
        return balance;
    }

    public int getState() {
        return state;
    }
}
