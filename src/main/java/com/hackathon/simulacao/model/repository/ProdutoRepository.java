package com.hackathon.simulacao.model.repository;

import com.hackathon.simulacao.model.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByVrMinimoLessThanEqualOrderByVrMinimoDesc(BigDecimal valorDesejado);
}
