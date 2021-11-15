package com.ebanking.ebanking.service;

import com.ebanking.ebanking.dao.DAOClasses.TransactionDAO;
import com.ebanking.ebanking.dao.DAOInterfaces.AccountDAO;
import com.ebanking.ebanking.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionDAO transactionDAO;

    @Autowired
    public TransactionService(@Qualifier("postgrestr") TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    public void createTransaction(int destAcc, int sourceAcc, float destBal, float sourceBal, float amount){
        this.transactionDAO.createTransaction(new Transaction(destAcc,sourceAcc,destBal,sourceBal,amount, LocalDateTime.now()));
    }

    public List getTransactions(int accNo, LocalDateTime fromDate, LocalDateTime toDate){
        return transactionDAO.getTransactions(accNo,fromDate,toDate);
    }
}
