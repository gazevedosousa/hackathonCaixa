package com.hackaton.simulacao.service.impl;

import com.hackaton.simulacao.exceptions.RegraNegocioException;
import com.hackaton.simulacao.model.entity.Produto;
import com.hackaton.simulacao.model.repository.ProdutoRepository;
import com.hackaton.simulacao.service.ProdutoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository repository;

    @Override
    public Produto buscarProdutoPorValor(BigDecimal valorDesejado) throws RegraNegocioException {
        List<Produto> produtosList = repository.buscaProdutoPorValor((valorDesejado));

        if(produtosList.isEmpty()){
            throw new RegraNegocioException("Valor desejado inferior ao mínimo de R$200,00");
        }

        return produtosList.get(0);
    }
}
