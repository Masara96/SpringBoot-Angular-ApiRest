package com.programa.springboot.backend.apirest.service;

import java.util.List;

import com.programa.springboot.backend.apirest.models.entity.Cliente;

public interface IServiceCliente {
    
	public List<Cliente> findAll();
	
	public Cliente findById(Long id);
	
	public void deleteById(Long id);
	
	public Cliente save(Cliente cliente);
	
	
	
}
