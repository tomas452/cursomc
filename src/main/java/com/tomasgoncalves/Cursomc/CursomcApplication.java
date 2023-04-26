package com.tomasgoncalves.Cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tomasgoncalves.Cursomc.domain.Categoria;
import com.tomasgoncalves.Cursomc.domain.Cidade;
import com.tomasgoncalves.Cursomc.domain.Cliente;
import com.tomasgoncalves.Cursomc.domain.Endereco;
import com.tomasgoncalves.Cursomc.domain.Estado;
import com.tomasgoncalves.Cursomc.domain.ItemPedido;
import com.tomasgoncalves.Cursomc.domain.Pagamento;
import com.tomasgoncalves.Cursomc.domain.PagamentoComBoleto;
import com.tomasgoncalves.Cursomc.domain.PagamentoComCartao;
import com.tomasgoncalves.Cursomc.domain.Pedido;
import com.tomasgoncalves.Cursomc.domain.Produto;
import com.tomasgoncalves.Cursomc.domain.enums.EstadoPagamento;
import com.tomasgoncalves.Cursomc.domain.enums.TipoCliente;
import com.tomasgoncalves.Cursomc.repositories.CategoriaRepository;
import com.tomasgoncalves.Cursomc.repositories.CidadeRepository;
import com.tomasgoncalves.Cursomc.repositories.ClienteRepository;
import com.tomasgoncalves.Cursomc.repositories.EnderecoRepository;
import com.tomasgoncalves.Cursomc.repositories.EstadoRepository;
import com.tomasgoncalves.Cursomc.repositories.ItemPedidoRrepository;
import com.tomasgoncalves.Cursomc.repositories.PagamentoRepository;
import com.tomasgoncalves.Cursomc.repositories.PedidoRepository;
import com.tomasgoncalves.Cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRrepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Rato", 80.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		Estado est1 = new Estado(null, "Braga");
		Estado est2 = new Estado(null, "Porto");

		Cidade c1 = new Cidade(null, "Guimarães", est1);
		Cidade c2 = new Cidade(null, "Porto", est2);
		Cidade c3 = new Cidade(null, "Santo Tirso", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "Tomás Gonçalves", "maria@gmail.com", "667999857", TipoCliente.PessoaFisica);
		cli1.getTelefones().addAll(Arrays.asList("227363323", "93838393"));

		Endereco e1 = new Endereco(null, "Rua Paulo VI", "6", "Apto 1", "Lordelo", "4815-204", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "10", "Apto 2", "Boavista", "4800", cli1, c2);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"),
				null);
		ped2.setPagamento(pagto2);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 80.00);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));

		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
		
		
	}

}
