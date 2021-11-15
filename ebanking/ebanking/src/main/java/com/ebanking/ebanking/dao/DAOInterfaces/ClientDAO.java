package com.ebanking.ebanking.dao.DAOInterfaces;

import com.ebanking.ebanking.model.Client;

import java.util.List;

public interface ClientDAO {

    int createClient(Client client);
    Client getClient(int id);
    Client getClient(String email, String pass, String uuid);
    List getClients();
    boolean validToken(String token);
    boolean isAdmin(int id);
}
