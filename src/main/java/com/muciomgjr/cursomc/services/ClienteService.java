package com.muciomgjr.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muciomgjr.cursomc.domain.Cliente;
import com.muciomgjr.cursomc.repositories.ClienteRepository;
import com.muciomgjr.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository cliRepo;
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> obj = cliRepo.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id:" + id + ", Tipo: "+Cliente.class.getName()
				));

	}
}
