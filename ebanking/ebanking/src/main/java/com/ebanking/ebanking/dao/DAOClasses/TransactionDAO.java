package com.ebanking.ebanking.dao.DAOClasses;

import com.ebanking.ebanking.model.Account;
import com.ebanking.ebanking.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;

@Repository("postgrestr")
public class TransactionDAO implements com.ebanking.ebanking.dao.DAOInterfaces.TransactionDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TransactionDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int createTransaction(Transaction tr) {
        jdbcTemplate.update("INSERT INTO transaction (dest_account,source_account,source_balance,dest_balance,amount,date) VALUES (?,?,?,?,?,?)",tr.getDestAccount(),
                tr.getSourceAccount(),tr.getSourceBalance(),tr.getDestBalance(),tr.getAmount(),tr.getLocalDate());
        return 1;
    }

    @Override
    public Transaction getTransaction(int id) {
        return null;
    }

    @Override
    public List getTransactions(int accountNo) {
        return null;
    }

    @Override
    public List getTransactions(int accountNo, LocalDateTime fromDate, LocalDateTime toDate) {
        List<Transaction> list = new ArrayList<>();

        return jdbcTemplate.query("SELECT * FROM transaction WHERE (source_account = ? OR dest_account = ?) AND date >= ? AND date <= ?", (ResultSetExtractor<List>) rs -> {
            while(rs.next()){
                list.add(new Transaction(Integer.parseInt(rs.getString("dest_account")), Integer.parseInt(rs.getString("source_account")),
                        Float.parseFloat(rs.getString("dest_balance")), Float.parseFloat(rs.getString("source_balance")),
                        Float.parseFloat(rs.getString("amount")),rs.getTimestamp("date").toLocalDateTime())
                );
            }
            return list;
        }, accountNo, accountNo, fromDate, toDate);
    }
}
