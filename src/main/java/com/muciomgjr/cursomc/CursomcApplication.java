package com.muciomgjr.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.muciomgjr.cursomc.domain.Categoria;
import com.muciomgjr.cursomc.domain.Cidade;
import com.muciomgjr.cursomc.domain.Cliente;
import com.muciomgjr.cursomc.domain.Endereco;
import com.muciomgjr.cursomc.domain.Estado;
import com.muciomgjr.cursomc.domain.ItemPedido;
import com.muciomgjr.cursomc.domain.Pagamento;
import com.muciomgjr.cursomc.domain.PagamentoComBoleto;
import com.muciomgjr.cursomc.domain.PagamentoComCartao;
import com.muciomgjr.cursomc.domain.Pedido;
import com.muciomgjr.cursomc.domain.Produto;
import com.muciomgjr.cursomc.domain.enums.EstadoPagamento;
import com.muciomgjr.cursomc.domain.enums.TipoCliente;
import com.muciomgjr.cursomc.repositories.CategoriaRepository;
import com.muciomgjr.cursomc.repositories.CidadeRepository;
import com.muciomgjr.cursomc.repositories.ClienteRepository;
import com.muciomgjr.cursomc.repositories.EnderecoRepository;
import com.muciomgjr.cursomc.repositories.EstadoRepository;
import com.muciomgjr.cursomc.repositories.ItemPedidoRepository;
import com.muciomgjr.cursomc.repositories.PagamentoRepository;
import com.muciomgjr.cursomc.repositories.PedidoRepository;
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
	@Autowired
	private ClienteRepository cliRepo;
	@Autowired
	private EnderecoRepository endRepo;
	@Autowired
	private PedidoRepository pedidoRepo;
	@Autowired
	private PagamentoRepository pagaRepo;
	@Autowired
	private ItemPedidoRepository itemPedRepo;
	
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
		
		
		Cliente cli01 = new Cliente(null, "Anna Silva", "anninha@hotmail.com", "69697859931", TipoCliente.PESSOAFISICA);
		cli01.getTelefone().addAll(Arrays.asList("6199637859","619969869"));
	
		Endereco end01 = new Endereco(null, "Rua Flores", "300", "Apt 303", "Jardim", "38220834", cli01, cit1);
		Endereco end02 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli01, cit2);
	
		cli01.getEnderecos().addAll(Arrays.asList(end01, end02));
		
		cliRepo.saveAll(Arrays.asList(cli01));
		endRepo.saveAll(Arrays.asList(end01, end02));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido pedido1 = new Pedido(null, sdf.parse("26/08/2020 10:32"), cli01, end01);
		Pedido pedido2 = new Pedido(null, sdf.parse("08/08/2020 19:37"), cli01, end02);
	
		Pagamento pagto1 = new PagamentoComCartao(null,EstadoPagamento.QUITADO,pedido1,6);
		pedido1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, pedido2,
				sdf.parse("12/08/2020 00:00"), null);
		pedido2.setPagamento(pagto2);
		
		cli01.getPedidos().addAll(Arrays.asList(pedido1, pedido2));
	
		pedidoRepo.saveAll(Arrays.asList(pedido1, pedido2));
		pagaRepo.saveAll(Arrays.asList(pagto1, pagto2));
		
		
		ItemPedido ip1 = new ItemPedido(pedido1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(pedido1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(pedido2, p2, 100.00, 1, 800.00);
		
		pedido1.getItens().addAll(Arrays.asList(ip1,ip2));
		pedido2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedRepo.saveAll(Arrays.asList(ip1,ip2,ip3));
	
	}

}
