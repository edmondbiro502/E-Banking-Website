package com.ebanking.ebanking.service;

import com.ebanking.ebanking.dao.DAOInterfaces.ClientDAO;
import com.ebanking.ebanking.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientDAO clientDAO;

    @Autowired
    public ClientService(@Qualifier("postgres") ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public int addClient(Client client){
        clientDAO.createClient(client);
        return 1;
    }

    public Client logIn(String email, String pass, String uuid) {
        return clientDAO.getClient(email, pass, uuid);
    }

    public Client getClient(int id){
        return clientDAO.getClient(id);
    }

    public List getClientsId(){
        return clientDAO.getClients();
    }

    public boolean idValidToken(String token){
        return clientDAO.validToken(token);
    }

    public boolean isAdmin(int id){
        return clientDAO.isAdmin(id);
    }
}
