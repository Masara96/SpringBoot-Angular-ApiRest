package com.programa.springboot.backend.apirest.repository;

import org.springframework.data.repository.CrudRepository;

import com.programa.springboot.backend.apirest.models.entity.Cliente;


public interface IRepositoryCliente extends CrudRepository<Cliente, Long> {
       
   
 
}
