package com.ebanking.ebanking.dao.DAOClasses;

import com.ebanking.ebanking.model.Account;
import com.ebanking.ebanking.model.Client;
import jdk.jfr.Registered;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("postgres")
public class ClientDAO implements com.ebanking.ebanking.dao.DAOInterfaces.ClientDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClientDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int createClient(Client client) {
        jdbcTemplate.update("INSERT INTO client (email,firstname,lastname,username,password,admin) VALUES (?,?,?,?,?,0)",client.getEmail(),client.getFirstName(),
                client.getLastName(),client.getUsername(),client.getPassword());
        return 0;
    }

    @Override
    public Client getClient(int id) {
        String sql ="SELECT * FROM client WHERE id = ?";

        Client client = jdbcTemplate.queryForObject(sql, (resultSet, i) -> {return new Client(Integer.parseInt(resultSet.getString("id")),
                resultSet.getString("email"),resultSet.getString("firstname"),
                resultSet.getString("lastname"), resultSet.getString("username"),
                resultSet.getString("password"), Integer.parseInt(resultSet.getString("admin")),
                resultSet.getString("jwt"));
        }, id);

        return client;
    }

    @Override
    public Client getClient(String email, String pass, String uuid) {
        String sql ="SELECT * FROM client WHERE email = ? AND password = ?";

        jdbcTemplate.update("UPDATE client SET jwt = ? WHERE email = ? AND password = ?",uuid,email,pass);

        Client client = jdbcTemplate.queryForObject(sql, (resultSet, i) -> new Client(Integer.parseInt(resultSet.getString("id")),
                resultSet.getString("email"),resultSet.getString("firstname"),
                resultSet.getString("lastname"), resultSet.getString("username"),
                resultSet.getString("password"), Integer.parseInt(resultSet.getString("admin")),
                resultSet.getString("jwt")), email, pass);

        return client;
    }

    @Override
    public List getClients() {
        List<Integer> list = new ArrayList<>();

        return jdbcTemplate.query("SELECT id FROM client", (ResultSetExtractor<List>) rs -> {
            while(rs.next()){
                list.add(Integer.parseInt(rs.getString("id")));
            }
            return list;
        });
    }

    @Override
    public boolean validToken(String token){
        Integer count = 0;
        count = jdbcTemplate.queryForObject("SELECT COUNT(*) as nb FROM client WHERE jwt = ?", (resultSet, i) -> Integer.parseInt(resultSet.getString("nb")), token);
        assert count != null;
        return count == 1;
    }

    @Override
    public boolean isAdmin(int id) {
        Integer count = 0;
        count = jdbcTemplate.queryForObject("SELECT admin FROM client WHERE id = ?", (resultSet, i) -> Integer.parseInt(resultSet.getString("admin")), id);
        assert count != null;
        return count == 1;
    }


}
