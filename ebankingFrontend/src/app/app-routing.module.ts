import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { NavBarComponent } from './nav-bar/nav-bar.component'
import { MainPageComponent } from './main-page/main-page.component';
import { LoginPageComponent } from './login-page/login-page.component';
import  Auth from './Auth'
import { RegisterComponent } from './register/register.component';
import { AuthGuard } from './auth.guard';


const routes: Routes = [
  
  { path:'home', loadChildren: () => import('./admin/admin.module')
    .then(m =>  m.AdminModule), canActivate: [AuthGuard], },
  { path:'register', component: RegisterComponent, pathMatch: 'full'},
  { path:'login', component: LoginPageComponent, pathMatch: 'full'},
  { path:'**', redirectTo: '/login'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const routingComponents = [MainPageComponent]