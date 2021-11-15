package com.ebanking.ebanking.dao.DAOInterfaces;

import com.ebanking.ebanking.model.Client;
import com.ebanking.ebanking.model.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionDAO {
    int createTransaction(Transaction client);
    Transaction getTransaction(int id);
    List getTransactions(int accountNo);
    List getTransactions(int accountNo, LocalDateTime fromDate, LocalDateTime toDate);
}
