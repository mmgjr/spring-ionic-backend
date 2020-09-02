package com.muciomgjr.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.muciomgjr.cursomc.domain.Categoria;
import com.muciomgjr.cursomc.domain.Cliente;
import com.muciomgjr.cursomc.dto.CategoriaDTO;
import com.muciomgjr.cursomc.dto.ClienteDTO;
import com.muciomgjr.cursomc.dto.ClienteNewDTO;
import com.muciomgjr.cursomc.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> listar(@PathVariable Integer id){
		
		Cliente cliente = service.find(id);
		return ResponseEntity.ok().body(cliente);
	}
	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(
			@Valid @RequestBody ClienteDTO objDTO,
			@PathVariable Integer id){
		Cliente obj = service.fromDTO(objDTO);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDTO){
		Cliente obj = service.fromDTO(objDTO);
		
		obj = service.insert(obj);
		
		//Método POST após inserir um obj ele precisa retornar 201 com uma URI.
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll(){
		
		List<Cliente> listCategory = service.findAll();
		
		List<ClienteDTO> listDTO = listCategory.stream()
				.map( obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDTO);
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
		 	@RequestParam(value = "page", defaultValue = "0") Integer page,
		 	@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
		 	@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
		 	@RequestParam(value = "direction", defaultValue = "ASC") String direction
		){
		
		Page<Cliente> pageCategory = service.findPage(page, linesPerPage, orderBy, direction);
		
		Page<ClienteDTO> listDTO = pageCategory.map( obj -> new ClienteDTO(obj));
		
		return ResponseEntity.ok().body(listDTO);
	}
	
	
}

