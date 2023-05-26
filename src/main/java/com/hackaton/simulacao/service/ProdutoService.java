package com.hackaton.simulacao.service;

import com.hackaton.simulacao.api.dto.ParcelaDTO;
import com.hackaton.simulacao.api.dto.ResponseDTO;
import com.hackaton.simulacao.api.dto.ResultadoSimulacaoDTO;
import com.hackaton.simulacao.model.entity.Produto;

import java.math.BigDecimal;
import java.util.List;

public interface ProdutoService {
    ResponseDTO buscarProdutoPorValor(BigDecimal valorDesejado, int prazo);
    ResultadoSimulacaoDTO calculaPrice(BigDecimal taxaJuros, int prazo, BigDecimal valorDesejado);
    ResultadoSimulacaoDTO calculaSAC(BigDecimal taxaJuros, int prazo, BigDecimal valorDesejado);
}
