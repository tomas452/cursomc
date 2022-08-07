package pt.tomas.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import pt.tomas.cursomc.domain.Categoria;
import pt.tomas.cursomc.domain.Cidade;
import pt.tomas.cursomc.domain.Cliente;
import pt.tomas.cursomc.domain.Endereco;
import pt.tomas.cursomc.domain.Estado;
import pt.tomas.cursomc.domain.Produto;
import pt.tomas.cursomc.domain.enums.TipoCliente;
import pt.tomas.cursomc.repositories.CategoriaRepository;
import pt.tomas.cursomc.repositories.CidadeRepository;
import pt.tomas.cursomc.repositories.ClienteRepository;
import pt.tomas.cursomc.repositories.EnderecoRepository;
import pt.tomas.cursomc.repositories.EstadoRepository;
import pt.tomas.cursomc.repositories.ProdutoRepository;

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
	@Autowired EnderecoRepository enderecoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

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
		Cidade c3 = new Cidade(null, "Vizela", est1);

		est1.getCidades().addAll(Arrays.asList(c1, c3));
		est2.getCidades().addAll(Arrays.asList(c2));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "Tomás Gonçalves", "tomasricardo1975@outlook.pt", "198334559",
				TipoCliente.PESSOAFISICA);

		cli1.getTelefones().addAll(Arrays.asList("911548133"));
		Endereco e1 = new Endereco(null, "Rua Paulo VI", "6", "Lordelo", c1, "4815-204", cli1);
		Endereco e2 = new Endereco(null, "Rua de martim", "9", "Nespereira", c1, "4835-514", cli1);
		cli1.getEndereco().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));

	}
}
