package com.ebanking.ebanking.controller;

import com.ebanking.ebanking.model.Account;
import com.ebanking.ebanking.model.Transaction;
import com.ebanking.ebanking.service.AccountService;
import com.ebanking.ebanking.service.ClientService;
import com.ebanking.ebanking.service.TransactionService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@RequestMapping("/transaction")
@RestController
public class TransactionController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;
    private final TransactionService transactionService;


    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/create/{jwt}")
    public int addTransaction(@RequestBody Transaction transaction, @PathVariable("jwt") String jwt){
        if(clientService.idValidToken(jwt))
            try {
                Account source = accountService.getAccount(transaction.getSourceAccount());
                Account dest = accountService.getAccount(transaction.getDestAccount());

                accountService.deposit(dest.getAccountNo(),transaction.getAmount());
                accountService.deposit(source.getAccountNo(),-1*transaction.getAmount());

                source = accountService.getAccount(transaction.getSourceAccount());
                dest = accountService.getAccount(transaction.getDestAccount());

                transactionService.createTransaction(transaction.getDestAccount(),transaction.getSourceAccount(),
                        dest.getBalance(),source.getBalance(),transaction.getAmount());

                return 1;
            }catch(Exception e){
                System.out.println(e);
                return 0;}
        else return 0;
    }

    @GetMapping("/getTransactions/{id}/{fromYear}/{fromMonth}/{fromDay}/{toYear}/{toMonth}/{toDay}/{jwt}")
    public List<Account> getAccounts(@PathVariable("id") int id, @PathVariable("fromYear") int fromYear,@PathVariable("fromMonth") int fromMonth,
                                     @PathVariable("fromDay") int fromDay, @PathVariable("toYear") int toYear,@PathVariable("toMonth") int toMonth,
                                     @PathVariable("toDay") int toDay, @PathVariable("jwt") String jwt)
    {
        if(clientService.idValidToken(jwt))
            try {
                List list;
                list = transactionService.getTransactions(id, LocalDateTime.of(LocalDate.of(fromYear, fromMonth, fromDay), LocalTime.of(0,0)),
                        LocalDateTime.of(LocalDate.of(toYear, toMonth, toDay), LocalTime.of(23,59)));
                if(list.size()>0)
                    return list;
                return null;
            }catch(Exception e){
                System.out.println(e);
                return null;
            }

        else return null;
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("destAccount", "sourceAccount", "destBalance", "sourceBalance", "amount", "localDate")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);

                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table, Transaction t) {
        table.addCell(String.valueOf(t.getDestAccount()));
        table.addCell(String.valueOf(t.getSourceAccount()));
        table.addCell(String.valueOf(t.getDestBalance()));
        table.addCell(String.valueOf(t.getSourceBalance()));
        table.addCell(String.valueOf(t.getAmount()));
        table.addCell(String.valueOf(t.getLocalDate().toLocalDate()));
    }

    @GetMapping("/downloadPDF/{id}/{fromYear}/{fromMonth}/{fromDay}/{toYear}/{toMonth}/{toDay}/{jwt}")
    public void downloadPDF(HttpServletResponse response,@PathVariable("id") int id, @PathVariable("fromYear") int fromYear,@PathVariable("fromMonth") int fromMonth,
                            @PathVariable("fromDay") int fromDay, @PathVariable("toYear") int toYear,@PathVariable("toMonth") int toMonth,
                            @PathVariable("toDay") int toDay, @PathVariable("jwt") String jwt) throws FileNotFoundException, DocumentException
    {

        if(clientService.idValidToken(jwt))
        {
            String fileName = String.valueOf(id);

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=" +fileName);
            response.setHeader("Content-Transfer-Encoding", "download");

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\edmon\\Desktop\\ebanking\\ebanking\\src\\main\\resources\\pdf\\pdf"+fileName+".pdf"));

            document.open();

            List<Transaction> list;
            list = transactionService.getTransactions(id, LocalDateTime.of(LocalDate.of(fromYear, fromMonth, fromDay), LocalTime.of(0,0)),
                    LocalDateTime.of(LocalDate.of(toYear, toMonth, toDay), LocalTime.of(23,59)));

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            addTableHeader(table);

            for(Transaction a : list)
                  addRows(table, a);

            document.add(table);
            document.close();

            try {
                BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
                FileInputStream fis = new FileInputStream("C:\\Users\\edmon\\Desktop\\ebanking\\ebanking\\src\\main\\resources\\pdf\\pdf"+fileName+".pdf");
                int len;
                byte[] buf = new byte[1024];
                while((len = fis.read(buf)) > 0) {
                    bos.write(buf,0,len);
                }
                bos.close();
                response.flushBuffer();
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("/downloadTXT/{id}/{fromYear}/{fromMonth}/{fromDay}/{toYear}/{toMonth}/{toDay}/{jwt}")
    public void downloadTXT(HttpServletResponse response,@PathVariable("id") int id, @PathVariable("fromYear") int fromYear,@PathVariable("fromMonth") int fromMonth,
                            @PathVariable("fromDay") int fromDay, @PathVariable("toYear") int toYear,@PathVariable("toMonth") int toMonth,
                            @PathVariable("toDay") int toDay, @PathVariable("jwt") String jwt) throws FileNotFoundException, DocumentException
    {
        if(clientService.idValidToken(jwt))
        {
            String fileName = String.valueOf(id);

            response.setContentType("application/txt");
            response.setHeader("Content-Disposition", "attachment; filename=" +fileName);
            response.setHeader("Content-Transfer-Encoding", "download");

            List<Transaction> list;
            list = transactionService.getTransactions(id, LocalDateTime.of(LocalDate.of(fromYear, fromMonth, fromDay), LocalTime.of(0,0)),
                    LocalDateTime.of(LocalDate.of(toYear, toMonth, toDay), LocalTime.of(23,59)));

            try {
                FileWriter myWriter = new FileWriter("C:\\Users\\edmon\\Desktop\\ebanking\\ebanking\\src\\main\\resources\\txt\\txt"+fileName+".txt");
                for(Transaction a : list)
                    myWriter.write(a.toString()+"\n");
                myWriter.close();

            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

            try {
                BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
                FileInputStream fis = new FileInputStream("C:\\Users\\edmon\\Desktop\\ebanking\\ebanking\\src\\main\\resources\\txt\\txt"+fileName+".txt");
                int len;
                byte[] buf = new byte[1024];
                while((len = fis.read(buf)) > 0) {
                    bos.write(buf,0,len);
                }
                bos.close();
                response.flushBuffer();
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("/downloadHTML/{id}/{fromYear}/{fromMonth}/{fromDay}/{toYear}/{toMonth}/{toDay}/{jwt}")
    public void downloadHTML(HttpServletResponse response,@PathVariable("id") int id, @PathVariable("fromYear") int fromYear,@PathVariable("fromMonth") int fromMonth,
                            @PathVariable("fromDay") int fromDay, @PathVariable("toYear") int toYear,@PathVariable("toMonth") int toMonth,
                            @PathVariable("toDay") int toDay, @PathVariable("jwt") String jwt) throws FileNotFoundException, DocumentException
    {
        if(clientService.idValidToken(jwt))
        {
            String fileName = String.valueOf(id);

            response.setContentType("application/html");
            response.setHeader("Content-Disposition", "attachment; filename=" +fileName);
            response.setHeader("Content-Transfer-Encoding", "download");

            List<Transaction> list;
            list = transactionService.getTransactions(id, LocalDateTime.of(LocalDate.of(fromYear, fromMonth, fromDay), LocalTime.of(0,0)),
                    LocalDateTime.of(LocalDate.of(toYear, toMonth, toDay), LocalTime.of(23,59)));

            try {
                File f = new File("C:\\Users\\edmon\\Desktop\\ebanking\\ebanking\\src\\main\\resources\\html\\html"+fileName+".html");
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                bw.write("<html><body><h1>Extras Cont</h1>");

                for(Transaction a : list)
                    bw.write("<p>"+a.toString()+"</p>");
                bw.write("</body></html>");
                bw.close();

            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

            try {
                BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
                FileInputStream fis = new FileInputStream("C:\\Users\\edmon\\Desktop\\ebanking\\ebanking\\src\\main\\resources\\html\\html"+fileName+".html");
                int len;
                byte[] buf = new byte[1024];
                while((len = fis.read(buf)) > 0) {
                    bos.write(buf,0,len);
                }
                bos.close();
                response.flushBuffer();
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}
