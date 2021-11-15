package com.ebanking.ebanking.dao.DAOClasses;

import com.ebanking.ebanking.model.Account;
import com.ebanking.ebanking.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("postgresac")
public class AccountDAO implements com.ebanking.ebanking.dao.DAOInterfaces.AccountDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AccountDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public int createAccount(int accountNo, int clientId) {
        jdbcTemplate.update("INSERT INTO account (account_no,client_id,bank,state,balance) VALUES (?,?,?,?,?)",accountNo,clientId,"bank",3,0);
        return 0;
    }

    @Override
    public int createAccount(int accountNo, String bank, int clientId, float balance, int state) {
        return 0;
    }

    @Override
    public List<Account> getAccounts(int clientId) {
        List<Account> list = new ArrayList<>();

        return jdbcTemplate.query("SELECT * FROM account WHERE client_id = ?", (ResultSetExtractor<List>) rs -> {
            while(rs.next()){
                list.add(new Account(Integer.parseInt(rs.getString("account_no")),rs.getString("bank"),Integer.parseInt(rs.getString("client_id")),
                        Float.parseFloat(rs.getString("balance")),Integer.parseInt(rs.getString("state"))));
            }
            return list;
        }, clientId);
    }

    @Override
    public int extsts(int accNo) {
        String sql ="SELECT * FROM account WHERE account_no = ?";
        return jdbcTemplate.queryForObject(sql, (resultSet, i) -> {return 1;}, accNo) == 1 ? 1 : 0;
    }

    @Override
    public int deposit(int accountNo, float bal) {
        float newBalance = 0.0F;
        Account acc = getAccount(accountNo);
        newBalance += acc.getBalance();
        newBalance += bal;

        jdbcTemplate.update("UPDATE account SET balance = ? WHERE account_no = ?",newBalance,accountNo);
        return 1;
    }

    @Override
    public Account getAccount(int accId) {

        final Account acc = jdbcTemplate.queryForObject("SELECT * FROM account WHERE account_no = ?", (resultSet, i) -> {return new Account(Integer.parseInt(resultSet.getString("id")),
                Integer.parseInt(resultSet.getString("account_no")),resultSet.getString("bank"),
                Integer.parseInt(resultSet.getString("client_id")), Float.parseFloat(resultSet.getString("balance")),
                Integer.parseInt(resultSet.getString("state")));
        }, accId);

        return acc;
    }

    @Override
    public List getAllAccounts() {
        List<Account> list = new ArrayList<>();

        return jdbcTemplate.query("SELECT * FROM account WHERE state = 3", (ResultSetExtractor<List>) rs -> {
            while(rs.next()){
                list.add(new Account(Integer.parseInt(rs.getString("account_no")),rs.getString("bank"),Integer.parseInt(rs.getString("client_id")),
                        Float.parseFloat(rs.getString("balance")),Integer.parseInt(rs.getString("state"))));
            }
            return list;
        });

    }

    @Override
    public void activateAccount(int id) {
        jdbcTemplate.update("UPDATE account SET state = 2 WHERE account_no = ?",id);

    }

    @Override
    public void deactivateAccount(int id) {
        jdbcTemplate.update("UPDATE account SET state = 1 WHERE account_no = ?",id);
    }

}
