package com.muciomgjr.cursomc.services.validations;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.muciomgjr.cursomc.domain.Cliente;
import com.muciomgjr.cursomc.domain.enums.TipoCliente;
import com.muciomgjr.cursomc.dto.ClienteNewDTO;
import com.muciomgjr.cursomc.repositories.ClienteRepository;
import com.muciomgjr.cursomc.resources.exception.FieldMessage;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClienteInsert bla) {

	}

	@Override
	public boolean isValid(ClienteNewDTO objDTO, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		if(objDTO.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) 
				&& !BR.isValidCPF(objDTO.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}
		
		Cliente aux = repo.findByEmail(objDTO.getEmail());
		if(aux != null) {
			list.add(new FieldMessage("email","Email já existente"));
		}
		
		// incluir os teste, inserir erros na lista.
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();

		}

		return list.isEmpty();
	}

}
