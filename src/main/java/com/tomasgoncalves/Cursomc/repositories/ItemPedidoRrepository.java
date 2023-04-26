package com.tomasgoncalves.Cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tomasgoncalves.Cursomc.domain.ItemPedido;

@Repository
public interface ItemPedidoRrepository extends JpaRepository<ItemPedido, Integer> {

}
