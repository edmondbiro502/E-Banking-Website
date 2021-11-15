import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainPageComponent } from '../main-page/main-page.component';
import { NavBarComponent } from '../nav-bar/nav-bar.component';
import { HomeMenuComponentComponent } from '../home-menu-component/home-menu-component.component';
import { AccountManagementComponent } from '../account-management/account-management.component';
import { AccountListComponent } from '../account-list/account-list.component';
import { TransferComponent } from '../transfer/transfer.component';
import { DepositComponent } from '../deposit/deposit.component';
import { TransactionsComponent } from '../transactions/transactions.component';
import { AdminGrantsComponent } from '../admin-grants/admin-grants.component';

const routes: Routes = [
  {path:'', component: MainPageComponent, children:[
    {path:'', component: HomeMenuComponentComponent},
    {path:'admin', component: AccountManagementComponent},
    {path:'accounts', component: AccountListComponent},
    {path:'transfer', component: TransferComponent},
    {path:'deposit', component: DepositComponent},
    {path:'transactions', component: TransactionsComponent},
    {path:'confirmations', component: AdminGrantsComponent}
  ]},
  
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
