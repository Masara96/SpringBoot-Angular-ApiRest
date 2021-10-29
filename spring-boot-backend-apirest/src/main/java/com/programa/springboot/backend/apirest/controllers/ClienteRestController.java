package com.programa.springboot.backend.apirest.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.programa.springboot.backend.apirest.models.entity.Cliente;
import com.programa.springboot.backend.apirest.service.ServiceImpCliente;

@CrossOrigin(origins = { "http://localhost:4200", "*" })
@RestController
@RequestMapping("/api")
public class ClienteRestController {

	@Autowired
	private ServiceImpCliente serviceCliente;
	
	
	private static final Logger log = LoggerFactory.getLogger(ClienteRestController.class);


	@GetMapping("/clientes")
	public ResponseEntity<?> listClients() {

		Map<String, Object> response = new HashMap<>();
		List<Cliente> clientes = new ArrayList<Cliente>();

		try {
			clientes = serviceCliente.findAll();
		} catch (DataAccessException e) {
			response.put("mensaje", "ERROR : Al realizar la busqueda de clientes!");
			response.put("error", "ERROR : " + e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (clientes == null) {
			String mensaje = "INFO : No se cuentra registrado ningun cliente!";
			return new ResponseEntity<String>(mensaje, HttpStatus.NO_CONTENT);
		}

		response.put("mensaje", "Se han encotrado clientes en la BD!");
		response.put("clientes",clientes);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> seach(@PathVariable Long id) {

		Cliente cliente = null;
		Map<String, Object> response = new HashMap<>();
		try {
			cliente = serviceCliente.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error : al realizar la consulta en la BD");
			response.put("error", "ERROR :" + e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (cliente == null) {
			response.put("Mensaje", "El cliente ID ".concat(id.toString()).concat(" no existe el la BD!"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}

	@DeleteMapping("/clientes/delete/{id}")
	// @ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();

		Cliente ClienteEliminar = null;

		try {
			ClienteEliminar = serviceCliente.findById(id);
			serviceCliente.deleteById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el cliente en la BD.");
			response.put("error", "ERROR : " + e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		if (ClienteEliminar == null) {
			response.put("Mensaje", "Error : El cliente no se encuentra en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
		}

		response.put("mensaje", "Se ha eliminado el cliente correctamente");
		response.put("cliente", ClienteEliminar);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@PostMapping("/clientes")
	public ResponseEntity<?> save(@Valid @RequestBody Cliente cliente, BindingResult result) {

		Cliente clienteNew = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> ("El campo '" + err.getField() + "' " + err.getDefaultMessage()))
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			clienteNew = serviceCliente.save(cliente);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error : al realizar la insert en la BD");
			response.put("error", "ERROR :" + e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El cliente ha sido creado con exito");
		response.put("cliente", clienteNew);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping("/clientes/update")
	// @ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result) {
        
		log.warn("ENTROOO");
		
		Cliente clienteActual = null;

		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			 
			log.warn("ENTROOO");

			List<String> errores = result.getFieldErrors().stream()
					.map(err -> ("El campo '" + err.getField() + "'" + err.getDefaultMessage()))
					.collect(Collectors.toList());

			response.put("errores", errores);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			
			clienteActual = serviceCliente.findById(cliente.getId());
			clienteActual.setNombre(cliente.getNombre());
			clienteActual.setApellido(cliente.getApellido());
			clienteActual.setEmail(cliente.getEmail());
			clienteActual = serviceCliente.save(clienteActual);
		
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el cliente en la BD.");
			response.put("errores", "ERROR : " + e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (clienteActual == null) {
			response.put("Mensaje", "Error : El cliente no se encuentra en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
		}

		response.put("mensaje", "El cliente ha sido actualizado con exito");
		response.put("cliente", clienteActual);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

}
