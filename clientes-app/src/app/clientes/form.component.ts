import { Component, OnInit } from '@angular/core';
import { Cliente } from './Cliente';
import {ClienteService} from './cliente.service';
import {Router,ActivatedRoute} from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',

})
export class FormComponent implements OnInit {

  public cliente : Cliente = new Cliente();
  public title : string = "Crear Cliente";

  public errores : string[];

  constructor(private clienteService: ClienteService
    //El router se utiliza en esta instancia para direccionar a otra pagina despues de haber guadado el datos
    //el activate sirve
    ,private router: Router,private activateRouter:ActivatedRoute) { }

  ngOnInit(): void {
    this.cargarCliente();
  }


  cargarCliente():void{
    this.activateRouter.params.subscribe(params =>{
      let id = params['id']
       if(id){
         this.clienteService.getCliente(id).subscribe(
           (cliente) => this.cliente = cliente)
       }
    });
  }

  public create(): void{
    this.clienteService.create(this.cliente).subscribe(
      json => {
          this.router.navigate(['/clientes'])
          Swal.fire({
          position: 'center',
          icon: 'success',
          title: `El cliente ${json.cliente.nombre} se ha creado con exito!`,
          showConfirmButton: false,
          timer: 1500
          })
    },
    err => {
      this.errores = err.error.errors as string[];
      
    }
    );
  }


  update():void{
    this.clienteService.update(this.cliente).subscribe(
      json => {
          this.router.navigate(['/clientes'])
          Swal.fire({
          position: 'center',
          icon: 'success',
          title: `El cliente  ${json.cliente.nombre} se ha actualizado con exito!`,
          showConfirmButton: false,
          timer: 1500
          })
      }
    )
  }

}
