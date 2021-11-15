import { Component, OnInit } from '@angular/core';
import { HttpClient , HttpHeaders} from '@angular/common/http'
import { Router } from '@angular/router'

@Component({
  selector: 'app-admin-grants',
  templateUrl: './admin-grants.component.html',
  styleUrls: ['./admin-grants.component.css']
})
export class AdminGrantsComponent implements OnInit {

  accounts:any[] = [];

  constructor(private router: Router, private http: HttpClient) { }

  ngOnInit(): void {
    if(localStorage.getItem("admin") !== '1')
    {
      this.router.navigate(['/home']);
    }
    else{
      let header = {
        headers: new HttpHeaders({
          'Content-Type': 'application/json'
        })
      };
      
      let url = 'http://localhost:8080/account/getUnauthorisedAccounts/'+localStorage.getItem("clientId")+'/'+localStorage.getItem("JWT");
  
        this.http.get(url,header).toPromise().then((data:any) => {
          this.accounts = data;
      });
    }

    
  }

  generateAcceptAction(id:any){
    let header = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    }

    let url = 'http://localhost:8080/account/activateAccount/'+this.accounts[id].accountNo+'/'+localStorage.getItem("JWT")+'/'+localStorage.getItem("clientId");
    this.http.get(url,header).toPromise().then(async (data:any) => {
      this.accounts.splice(id,id+1);
    });
    
  }

  generateRejectAction(id:any){
    let header = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    }

    let url = 'http://localhost:8080/account/deactivateAccount/'+this.accounts[id].accountNo+'/'+localStorage.getItem("JWT")+'/'+localStorage.getItem("clientId");
    this.http.get(url,header).toPromise().then(async (data:any) => {
      this.accounts.splice(id,id+1);
    });
    
  }

}
