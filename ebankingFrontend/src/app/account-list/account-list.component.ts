import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router'
import { HttpClient , HttpHeaders} from '@angular/common/http'

@Component({
  selector: 'app-account-list',
  templateUrl: './account-list.component.html',
  styleUrls: ['./account-list.component.css']
})
export class AccountListComponent implements OnInit {

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
        this.accounts = data;
    });
  }

}
