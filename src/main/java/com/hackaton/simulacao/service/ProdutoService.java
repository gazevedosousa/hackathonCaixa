package com.hackaton.simulacao.service;

import com.hackaton.simulacao.model.entity.Produto;

import java.math.BigDecimal;
import java.util.List;

public interface ProdutoService {

    Produto buscarProdutoPorValor(BigDecimal valorDesejado);
}
