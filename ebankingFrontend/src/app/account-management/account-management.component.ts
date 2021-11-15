import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router'
import { HttpClient , HttpHeaders} from '@angular/common/http'

@Component({
  selector: 'app-account-management',
  templateUrl: './account-management.component.html',
  styleUrls: ['./account-management.component.css']
})
export class AccountManagementComponent implements OnInit {

  accounts:any[] = [];
  accNum:any = '';
  message:string = '';

  constructor(private router: Router, private http: HttpClient) { }

  ngOnInit(): void {
    
    let header = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    
    let url = 'http://localhost:8080/account/getAccounts/'+localStorage.getItem("clientId")+'/'+localStorage.getItem("JWT");

      this.http.get(url,header).toPromise().then((data:any) => {
        if(data !== null)
          this.accounts = data;
        else
        this.accounts = [];
    });
  }

  getNumber(event:any){
    this.accNum = Number.parseInt(event.target.value);
  }

  createAccount(event:any):void {
    if(this.accNum.toString().length === 8)
    {
    let header = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    
    let url = 'http://localhost:8080/account/getAccount/'+this.accNum+'/'+localStorage.getItem("JWT");

      this.http.get(url,header).toPromise().then((data:any) => {console.log(data);
        if(data === 0){

          let url = 'http://localhost:8080/account/create/'+localStorage.getItem("JWT");
          let body = {"accountNo":this.accNum,"clientId":localStorage.getItem("clientId")};

          this.http.post(url,body,header).toPromise().then((data:any) => {
            
            if(this.accounts.length === 0)
              this.accounts = [{"accountNo":this.accNum}]
            else
              this.accounts.push({"accountNo":this.accNum});
              this.accNum = '';
              
            this.message = 'Success';
            setInterval(() => this.message = '', 3000);
          });}
          else{
            setInterval(() => this.message = '', 3000);
            this.message = 'Account exists';
          }
      });
    }
    else{
      this.message = 'Invalid number';
      this.accNum = '';
      setInterval(() => this.message = '', 3000);
    }
  }

}
