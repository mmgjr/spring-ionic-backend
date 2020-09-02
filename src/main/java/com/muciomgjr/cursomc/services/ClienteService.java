package com.muciomgjr.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.muciomgjr.cursomc.domain.Cidade;
import com.muciomgjr.cursomc.domain.Cliente;
import com.muciomgjr.cursomc.domain.Endereco;
import com.muciomgjr.cursomc.domain.enums.TipoCliente;
import com.muciomgjr.cursomc.dto.ClienteDTO;
import com.muciomgjr.cursomc.dto.ClienteNewDTO;
import com.muciomgjr.cursomc.repositories.ClienteRepository;
import com.muciomgjr.cursomc.repositories.EnderecoRepository;
import com.muciomgjr.cursomc.services.exceptions.DataIntegrityException;
import com.muciomgjr.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository cliRepo;
	@Autowired
	private EnderecoRepository endRepo;
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = cliRepo.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id:" + id + ", Tipo: "+Cliente.class.getName()
				));

	}
	
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = cliRepo.save(obj);
		endRepo.saveAll(obj.getEnderecos());
		return obj;
	}
	
	
	public Cliente update(Cliente obj) {
		Cliente objDB = find(obj.getId());
		updateData(objDB, obj);
		return cliRepo.save(objDB);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			cliRepo.deleteById(id);			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não foi possível excluir o cliente,pois ele possui pedidos.");
		}
	}
	
	public List<Cliente> findAll() {
		return cliRepo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return cliRepo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDTO) {
		Cliente cliente = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), TipoCliente.toEnum(objDTO.getTipo()));
		Cidade cidade = new Cidade(objDTO.getCidadeId(), null, null);
		Endereco address = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(), cliente, cidade);
		
		cliente.getEnderecos().add(address);
		cliente.getTelefone().add(objDTO.getTelefone1());
		
		if(objDTO.getTelefone2() != null) {
			cliente.getTelefone().add(objDTO.getTelefone2());;
		}
		if(objDTO.getTelefone3() != null) {
			cliente.getTelefone().add(objDTO.getTelefone3());;
		}
		
		return cliente;
	}
	
	
	
	private void updateData(Cliente objDB,Cliente obj) {
		objDB.setNome(obj.getNome());
		objDB.setEmail(obj.getEmail());
	}
}
