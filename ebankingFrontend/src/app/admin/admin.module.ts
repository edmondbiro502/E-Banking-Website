import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MainPageComponent } from '../main-page/main-page.component';
import { AdminRoutingModule } from './admin-routing.module';
import { NavBarComponent } from '../nav-bar/nav-bar.component';

@NgModule({
  declarations: [
    MainPageComponent,
    NavBarComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule
  ],
  providers: [],
  bootstrap: [MainPageComponent]
})
export class AdminModule { }
