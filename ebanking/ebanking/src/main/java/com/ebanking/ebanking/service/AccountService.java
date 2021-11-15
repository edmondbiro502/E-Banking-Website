package com.ebanking.ebanking.service;

import com.ebanking.ebanking.dao.DAOInterfaces.AccountDAO;
import com.ebanking.ebanking.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountDAO accountDAO;

    @Autowired
    public AccountService(@Qualifier("postgresac") AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public void createAccount(int accountNo, int clientId){
        accountDAO.createAccount(accountNo,clientId);
    }

    public List<Account> getAccounts(int clientId){
        return accountDAO.getAccounts(clientId);
    }

    public List getAllAccounts(){
        return accountDAO.getAllAccounts();
    }

    public int exists(int id){
        return accountDAO.extsts(id);
    }

    public Account getAccount(int accountNo){
        return accountDAO.getAccount(accountNo);
    }

    public int deposit(int accountNo, float bal){
        return accountDAO.deposit(accountNo,bal);
    }

    public void activateAccount(int accNo){
        accountDAO.activateAccount(accNo);
    }

    public void deactivateAccount(int accNo){
        accountDAO.deactivateAccount(accNo);
    }
}
