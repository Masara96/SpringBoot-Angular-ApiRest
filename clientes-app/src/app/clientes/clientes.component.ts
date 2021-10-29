import { Component, OnInit } from '@angular/core';
import {Cliente} from './cliente';
//import { CLIENTES } from './clientes.json';
import {ClienteService} from './cliente.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html',

})

export class ClientesComponent implements OnInit {

   clientes: Cliente[];



   constructor(private clienteService: ClienteService) {
   }

  ngOnInit(): void {
    this.clienteService.getClientes().subscribe(
       (clientes) => this.clientes = clientes
    );
  }

  delete(cliente:Cliente):void{
    Swal.fire({
     title: 'Quiere eliminar al cliente '+`${cliente.nombre}`+'?',
     showDenyButton: true,
     showCancelButton: true,
     icon: 'error',
     confirmButtonText: 'Si',
     denyButtonText: `No`,
     }).then((result) => {
     /* Read more about isConfirmed, isDenied below */
     if (result.isConfirmed) {
       Swal.fire('Se ha eliminado al cliente', '', 'success')
       this.clienteService.deleteCliente(cliente.id).subscribe(
         respuesta =>{
           this.clientes = this.clientes.filter(cli => cli !== cliente)
         }
       )
     }
    })
  }

}
