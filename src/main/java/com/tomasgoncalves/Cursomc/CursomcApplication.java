package com.tomasgoncalves.Cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tomasgoncalves.Cursomc.domain.Categoria;
import com.tomasgoncalves.Cursomc.domain.Cidade;
import com.tomasgoncalves.Cursomc.domain.Estado;
import com.tomasgoncalves.Cursomc.domain.Produto;
import com.tomasgoncalves.Cursomc.repositories.CategoriaRepository;
import com.tomasgoncalves.Cursomc.repositories.CidadeRepository;
import com.tomasgoncalves.Cursomc.repositories.EstadoRepository;
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
	private CidadeRepository cidadeRopository;

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
		cidadeRopository.saveAll(Arrays.asList(c1, c2, c3));
	}

}
