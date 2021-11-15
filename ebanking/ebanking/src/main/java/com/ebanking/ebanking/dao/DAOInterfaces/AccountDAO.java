package com.ebanking.ebanking.dao.DAOInterfaces;

import com.ebanking.ebanking.model.Account;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public interface AccountDAO {
    int createAccount(int accountNo, int clientId);
    int createAccount(int accountNo, String bank, int clientId, float balance, int state);
    List<Account> getAccounts(int clientId);
    int extsts(int accNo);
    int deposit(int accountNo,float bal);
    Account getAccount(int accId);
    List getAllAccounts();
    void activateAccount(int id);
    void deactivateAccount(int id);

}
