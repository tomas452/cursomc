package com.tomasgoncalves.Cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomasgoncalves.Cursomc.domain.ItemPedido;
import com.tomasgoncalves.Cursomc.domain.PagamentoComBoleto;
import com.tomasgoncalves.Cursomc.domain.Pedido;
import com.tomasgoncalves.Cursomc.domain.enums.EstadoPagamento;
import com.tomasgoncalves.Cursomc.repositories.ItemPedidoRrepository;
import com.tomasgoncalves.Cursomc.repositories.PagamentoRepository;
import com.tomasgoncalves.Cursomc.repositories.PedidoRepository;
import com.tomasgoncalves.Cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ItemPedidoRrepository itemPedidoRrepository;

	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstant(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstant());
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRrepository.saveAll(obj.getItens());
		return obj;
	}
}
