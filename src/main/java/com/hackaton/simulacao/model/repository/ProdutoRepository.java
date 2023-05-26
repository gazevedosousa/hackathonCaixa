package com.hackaton.simulacao.model.repository;

import com.hackaton.simulacao.model.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query(value = "Select p from Produto p where p.VR_MINIMO <= :valorDesejado order by p.VR_MINIMO desc")
    List<Produto> buscaProdutoPorValor(BigDecimal valorDesejado);
}
