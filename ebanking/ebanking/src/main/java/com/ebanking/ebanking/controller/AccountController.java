package com.ebanking.ebanking.controller;

import com.ebanking.ebanking.model.Account;
import com.ebanking.ebanking.service.AccountService;
import com.ebanking.ebanking.service.ClientService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@RequestMapping("/account")
@RestController
public class AccountController {


    private final ClientService clientService;
    private final AccountService accountService;

    public AccountController(ClientService clientService, AccountService accountService) {
        this.clientService = clientService;
        this.accountService = accountService;
    }

    @PostMapping("/create/{jwt}")
    public int addAccount(@RequestBody Account account,@PathVariable("jwt") String jwt){
        try {
            if(clientService.idValidToken(jwt)) {
                accountService.createAccount(account.getAccountNo(),account.getClientId());
                return 1;
            }
            else return 0;
        }catch(Exception e){
            System.out.println(e);
            return 0;}
    }

    @GetMapping("/getAccounts/{id}/{jwt}")
    public List<Account> getAccounts(@PathVariable("id") int id,@PathVariable("jwt") String jwt){
        try {
            if(clientService.idValidToken(jwt))
            {
                List<Account> list;
                list = accountService.getAccounts(id);
                if(list.size()>0)
                    return list;
                return null;
            }
            else return null;
        }catch(Exception e){
            System.out.println(e);
            return null;}

    }

    @GetMapping("/getUnauthorisedAccounts/{id}/{jwt}")
    public List<Account> getUnauthorisedAccounts(@PathVariable("id") int id,@PathVariable("jwt") String jwt){
        try {
            if(clientService.idValidToken(jwt) && clientService.isAdmin(id))
            {
                List list;
                list = accountService.getAllAccounts();
                if(list.size()>0)
                    return list;
                return null;
            }
            else return null;
        }catch(Exception e){
            System.out.println(e);
            return null;}

    }

    @GetMapping("/getAccount/{id}/{jwt}")
    public int getAccount(@PathVariable("id") int id, @PathVariable("jwt") String jwt){
        try {
            if(clientService.idValidToken(jwt))
                return accountService.exists(id);
            return 0;
        }catch(Exception e){
            System.out.println(e);
            return 0;}
    }

    @PostMapping("/deposit/{jwt}")
    public int depositAccount(@RequestBody Account account, @PathVariable String jwt){
        try {
            if(clientService.idValidToken(jwt))
                {
                    accountService.deposit(account.getAccountNo(),account.getBalance());
                    return 1;
                }
            else return 0;
        }catch(Exception e){
            System.out.println(e);
            return 0;}

    }

    @GetMapping("/activateAccount/{id}/{jwt}/{accId}")
    public void activateAccount(@PathVariable("id") int id, @PathVariable("jwt") String jwt, @PathVariable("accId") int accId){
        try {
            if(clientService.idValidToken(jwt) && clientService.isAdmin(accId))
                 accountService.activateAccount(id);
        }catch(Exception e){
            System.out.println(e);
            }
    }

    @GetMapping("/deactivateAccount/{id}/{jwt}/{accId}")
    public void deactivateAccount(@PathVariable("id") int id, @PathVariable("jwt") String jwt, @PathVariable("accId") int accId){
        try {
            if(clientService.idValidToken(jwt) && clientService.isAdmin(accId))
                accountService.deactivateAccount(id);
        }catch(Exception e){
            System.out.println(e);
        }
    }

}
