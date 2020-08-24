package com.muciomgjr.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.muciomgjr.cursomc.domain.Categoria;
import com.muciomgjr.cursomc.domain.Cidade;
import com.muciomgjr.cursomc.domain.Estado;
import com.muciomgjr.cursomc.domain.Produto;
import com.muciomgjr.cursomc.repositories.CategoriaRepository;
import com.muciomgjr.cursomc.repositories.CidadeRepository;
import com.muciomgjr.cursomc.repositories.EstadoRepository;
import com.muciomgjr.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository catRepo; 
	@Autowired
	private ProdutoRepository proRepo;
	@Autowired
	private CidadeRepository citRepo;
	@Autowired
	private EstadoRepository estRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//Instanciando categorias
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		//Instanciando Produtos
		Produto p1 = new Produto(null,"Computador",2000.00);
		Produto p2 = new Produto(null,"Impressora",800.00);
		Produto p3 = new Produto(null,"Mouse",80.00);
		
		//Colocando produtos em categoria
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		//Persistindo no banco
		catRepo.saveAll(Arrays.asList(cat1, cat2));
		proRepo.saveAll(Arrays.asList(p1, p2, p3));
		
		//Instanciando Estado e Cidades. Settando as relações e persistindo no banco.
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade cit1 = new Cidade(null, "Uberlândia", est1); 
		Cidade cit2 = new Cidade(null, "São Paulo", est2); 
		Cidade cit3 = new Cidade(null, "Campinas", est2); 
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		est1.getCidades().addAll(Arrays.asList(cit1));
		est2.getCidades().addAll(Arrays.asList(cit2,cit3));
		
		estRepo.saveAll(Arrays.asList(est1, est2));
		citRepo.saveAll(Arrays.asList(cit1, cit2, cit3));
		
	}

}
