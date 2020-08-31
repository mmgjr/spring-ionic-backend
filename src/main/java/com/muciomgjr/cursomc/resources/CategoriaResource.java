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
import com.muciomgjr.cursomc.dto.CategoriaDTO;
import com.muciomgjr.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService catService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id){
		
		Categoria categoria = catService.find(id);
		return ResponseEntity.ok().body(categoria);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDTO){
		Categoria obj = catService.fromDTO(objDTO);
		obj = catService.insert(obj);
		//Método POST após inserir um obj ele precisa retornar 201 com uma URI.
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(
			@Valid @RequestBody CategoriaDTO objDTO,
			@PathVariable Integer id){
		Categoria obj = catService.fromDTO(objDTO);
		obj.setId(id);
		obj = catService.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		catService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll(){
		
		List<Categoria> listCategory = catService.findAll();
		
		List<CategoriaDTO> listDTO = listCategory.stream()
				.map( obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDTO);
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(
		 	@RequestParam(value = "page", defaultValue = "0") Integer page,
		 	@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
		 	@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
		 	@RequestParam(value = "direction", defaultValue = "ASC") String direction
		){
		
		Page<Categoria> pageCategory = catService.findPage(page, linesPerPage, orderBy, direction);
		
		Page<CategoriaDTO> listDTO = pageCategory.map( obj -> new CategoriaDTO(obj));
		
		return ResponseEntity.ok().body(listDTO);
	}
	
}

