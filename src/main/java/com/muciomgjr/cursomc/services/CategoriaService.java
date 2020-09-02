package com.muciomgjr.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.muciomgjr.cursomc.domain.Categoria;
import com.muciomgjr.cursomc.dto.CategoriaDTO;
import com.muciomgjr.cursomc.repositories.CategoriaRepository;
import com.muciomgjr.cursomc.services.exceptions.DataIntegrityException;
import com.muciomgjr.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository catRepo;
	
	public Categoria find(Integer id) {
		Optional<Categoria> obj = catRepo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id:" + id + ", Tipo: "+Categoria.class.getName()
				));

	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return catRepo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		Categoria objDB = find(obj.getId());
		updateData(objDB, obj);
		return catRepo.save(obj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			catRepo.deleteById(id);			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir categogia que possue produtos");
		}
	}
	
	public List<Categoria> findAll() {
		return catRepo.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return catRepo.findAll(pageRequest);
	}
	
	public Categoria fromDTO(CategoriaDTO objDTO) {
		return new Categoria(objDTO.getId(), objDTO.getNome());
	}
	
	private void updateData(Categoria objDB,Categoria obj) {
		objDB.setNome(obj.getNome());
	}
	
}
