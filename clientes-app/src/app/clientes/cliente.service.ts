import { Injectable } from '@angular/core';
import {formatDate} from '@angular/common';
import {Cliente} from './cliente';
//import { CLIENTES } from './clientes.json';
import { Observable , of, throwError } from 'rxjs'; // se utiliza para peticiones asincronicas y de forma reactiva.
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { map,catchError,  } from 'rxjs/operators'; //funciones para convertir un observable
import  Swal  from 'sweetalert2';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  private urlEndPoint: string = 'http://localhost:8080/api/clientes';

  constructor(private http: HttpClient, private router:Router) { }

  private httpHeader = new HttpHeaders({'content-type': 'application/json'});

  //Cuando se necesite obtener datos
  getClientes():Observable<any>{
    return this.http.get<any>(this.urlEndPoint,{headers: this.httpHeader}).pipe(
      map(response =>{
        let clientes = response.clientes as Cliente[];
        return clientes.map(cliente => {
          cliente.nombre = cliente.nombre.toUpperCase();
          cliente.createAt = formatDate(cliente.createAt,'dd-MM-yyyy','en-US');
          return cliente;
        });
      }),
      catchError(e =>{
        if(e.status == 400){
          return throwError(e);
        }
        Swal.fire({
          icon: 'error',
          title: 'Oops!',
          text: e.error.mensaje,
        });
        return throwError(e);
      })
    );
  }

  //Cuando se necesite obtener datos y ademas llevarlos, se tiene que marcar el header
  create(cliente: Cliente): Observable<any>{
    return this.http.post<any>(this.urlEndPoint,cliente,{headers: this.httpHeader}).pipe(
      catchError(e =>{ //obtinee los error en el flujo
        //errores e ya viene con el status
        if(e.status == 400){
          return throwError(e);
        }
        Swal.fire({
          icon: 'error',
          title: 'Oops!',
          text: e.error.mensaje,
        });
        return throwError(e);
      })
    );
  }

 //el pipe obtine los flux
  getCliente(id : number): Observable<Cliente>{
    return this.http.get<Cliente>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeader}).pipe(
      catchError(e => {
        this.router.navigate(['/clientes']);
        Swal.fire({
          icon: 'error',
          title: 'Oops!',
          text: e.error.mensaje,
        });
        return throwError(e);
      })
    );
  }

  update(cliente: Cliente):Observable<any>{
    return this.http.put<any>(`${this.urlEndPoint}/`+'update',cliente,{headers: this.httpHeader}).pipe(
      catchError( e => {
        Swal.fire({
          icon: 'error',
          title: 'Oops!',
          text: e.error.errores,
        });
        return throwError(e);
      })
    );
  }

  deleteCliente(id : number): Observable<any>{
    return this.http.delete<any>(`${this.urlEndPoint}/`+'delete'+`/${id}`,{headers: this.httpHeader}).pipe(
      catchError(e=>{
        Swal.fire({
          icon: 'error',
          title: 'Oops!',
          text: e.error.mensaje,
        });
        return throwError(e);
      })
    );
  }

}
