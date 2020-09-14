package com.muciomgjr.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.muciomgjr.cursomc.domain.Produto;
import com.muciomgjr.cursomc.dto.ProdutoDTO;
import com.muciomgjr.cursomc.resources.utils.URL;
import com.muciomgjr.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> listar(@PathVariable Integer id){
		
		Produto categoria = service.find(id);
		return ResponseEntity.ok().body(categoria);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
		 	@RequestParam(value = "nome", defaultValue = "0") String nome,
		 	@RequestParam(value = "categorias", defaultValue = "0") String categorias,
		 	@RequestParam(value = "page", defaultValue = "0") Integer page,
		 	@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
		 	@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
		 	@RequestParam(value = "direction", defaultValue = "ASC") String direction
		){
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> ids = URL.convertionToIntList(categorias);
		Page<Produto> list = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
		
		Page<ProdutoDTO> listDTO = list.map( obj -> new ProdutoDTO(obj));
		
		return ResponseEntity.ok().body(listDTO);
	}
	
}

