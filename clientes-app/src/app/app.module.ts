import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { ClientesComponent } from './clientes/clientes.component';
import {RouterModule, Routes} from '@angular/router';
import {HttpClientModule} from '@angular/common/http';
import { FormComponent } from './clientes/form.component'; // es el que permite utilizar los verbos get,put,delete,post
import { FormsModule } from '@angular/forms';//Necesario agreagar para crear formularios

const routes : Routes = [
       {path: '', redirectTo:'/clientes',pathMatch:'full'},
       {path: 'clientes', component: ClientesComponent},
       {path: 'clientes/form', component: FormComponent},
       {path: 'clientes/form/:id', component: FormComponent},

];




@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    ClientesComponent,
    FormComponent,

  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes),
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
