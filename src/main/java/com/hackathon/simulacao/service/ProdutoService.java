package com.hackathon.simulacao.service;

import com.hackathon.simulacao.api.dto.ResponseDTO;
import com.hackathon.simulacao.api.dto.ResultadoSimulacaoDTO;

import java.math.BigDecimal;

public interface ProdutoService {
    ResponseDTO buscarProdutoPorValor(BigDecimal valorDesejado, int prazo);
    ResultadoSimulacaoDTO calculaPrice(BigDecimal taxaJuros, int prazo, BigDecimal valorDesejado);
    ResultadoSimulacaoDTO calculaSAC(BigDecimal taxaJuros, int prazo, BigDecimal valorDesejado);
    void enviaEventHub(ResponseDTO jsonSimulacao);
}
