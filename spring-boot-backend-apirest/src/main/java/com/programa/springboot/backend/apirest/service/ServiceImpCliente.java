package com.programa.springboot.backend.apirest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programa.springboot.backend.apirest.models.entity.Cliente;
import com.programa.springboot.backend.apirest.repository.IRepositoryCliente;

@Service
public class ServiceImpCliente implements IServiceCliente {
    
	@Autowired
	private IRepositoryCliente clienteDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findById(Long id) {
       return clienteDao.findById(id).orElse(null);
	}

	@Override
	public void deleteById(Long id) {
		clienteDao.deleteById(id);
	}

	@Override
	public Cliente save(Cliente cliente) {
	    return clienteDao.save(cliente);	
	}


	
	
}
