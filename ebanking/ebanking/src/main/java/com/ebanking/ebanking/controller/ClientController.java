package com.ebanking.ebanking.controller;

import com.ebanking.ebanking.model.Client;
import com.ebanking.ebanking.model.LoginJson;
import com.ebanking.ebanking.service.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/client")
@RestController
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService client) {
        this.clientService = client;
    }

    @PostMapping("/register")
    public int addClient(@RequestBody Client client){
        try {
            clientService.addClient(client);
            return 1;
        }catch(Exception e){
            return 0;}
    }

    @GetMapping("/getAll/{jwt}")
    public List getClientIds(@PathVariable("jwt") String jwt){
        try {
            if(clientService.idValidToken(jwt))
                return clientService.getClientsId();
            else return null;
        }catch(Exception e){
            return null;}
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginJson body){

        Client client = null;
        String uuid = UUID.randomUUID().toString();

        try{
            client = clientService.logIn(body.getEmail(), body.getPass(), uuid);
        }catch(Exception e)
        {
            System.out.println(e);
        }

        HashMap<String, String> map = new HashMap<>();
        if(client != null) {
            map.put("status", "Logged in");
            map.put("clientId", Integer.toString(client.getId()));
            map.put("firstName", client.getFirstName());
            map.put("lastName", client.getLastName());
            map.put("admin", String.valueOf(client.getAdmin()));
            map.put("jwt", client.getJwt());
        }

        return map;
    }

    @PostMapping("/getClient")
    public Map<String, String> getClient(@RequestBody LoginJson body){

        Client client = null;

        try{
            if(clientService.idValidToken(body.getJwt()))
                client = clientService.getClient(body.getId());
            else return null;
        }catch(Exception e)
        {System.out.println(e);}

        HashMap<String, String> map = new HashMap<>();
        if(client != null) {
            map.put("status", "Logged in");
            map.put("clientId", Integer.toString(client.getId()));
            map.put("firstName", client.getFirstName());
            map.put("lastName", client.getLastName());
            map.put("admin", String.valueOf(client.getAdmin()));
            map.put("jwt", client.getJwt());
        }

        return map;
    }

}
