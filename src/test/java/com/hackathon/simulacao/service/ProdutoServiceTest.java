package com.hackathon.simulacao.service;

import com.hackathon.simulacao.api.dto.ResponseDTO;
import com.hackathon.simulacao.exceptions.NotFoundException;
import com.hackathon.simulacao.service.impl.ProdutoServiceImpl;
import com.hackathon.simulacao.exceptions.RegraNegocioException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class ProdutoServiceTest {
    @Autowired
    ProdutoServiceImpl service;

    @Test(expected = NotFoundException.class)
    public void deveEnviarErroQuandoValorForMenorQue200() {
        //cenário
        BigDecimal valorDesejado = BigDecimal.valueOf(50);
        int prazo = 5;
        //validação
        service.buscarProdutoPorValor(valorDesejado, prazo);
    }

    @Test(expected = RegraNegocioException.class)
    public void deveEnviarErroQuandoPrazoForMenorIgualAZero() {
        //cenário
        BigDecimal valorDesejado = BigDecimal.valueOf(50000);
        int prazo = 0;
        //validação
        service.buscarProdutoPorValor(valorDesejado, prazo);
    }

    @Test(expected = RegraNegocioException.class)
    public void deveRetornarErroQuandoPrazoNaoForCompativelComValorDesejado() {
        //cenário
        BigDecimal valorDesejado = BigDecimal.valueOf(50000);
        int prazo = 5;
        //validação
        service.buscarProdutoPorValor(valorDesejado, prazo);
    }

    @Test(expected = Test.None.class)
    public void deveRetornarJsonComSimulacaoRealizada() {
        //cenário
        BigDecimal valorDesejado = BigDecimal.valueOf(900);
        int prazo = 5;
        //validação
        ResponseDTO response = service.buscarProdutoPorValor(valorDesejado, prazo);

        Assertions.assertEquals(1, response.getCodigoProduto());
        Assertions.assertEquals("Produto 1", response.getDescricaoProduto());
        Assertions.assertEquals(BigDecimal.valueOf(0.0179), response.getTaxaJuros());

        Assertions.assertEquals("SAC", response.getResultadoSimulacao().get(0).getTipo());
            Assertions.assertEquals(1, response.getResultadoSimulacao().get(0).getParcelas().get(0).getNumero());
            Assertions.assertEquals(BigDecimal.valueOf(180.00).setScale(2, RoundingMode.HALF_UP), response.getResultadoSimulacao().get(0).getParcelas().get(0).getValorAmortizacao());
            Assertions.assertEquals(BigDecimal.valueOf(16.11), response.getResultadoSimulacao().get(0).getParcelas().get(0).getValorJuros());
            Assertions.assertEquals(BigDecimal.valueOf(196.11), response.getResultadoSimulacao().get(0).getParcelas().get(0).getValorPrestacao());
            Assertions.assertEquals(2, response.getResultadoSimulacao().get(0).getParcelas().get(1).getNumero());
            Assertions.assertEquals(BigDecimal.valueOf(180.00).setScale(2, RoundingMode.HALF_UP), response.getResultadoSimulacao().get(0).getParcelas().get(1).getValorAmortizacao());
            Assertions.assertEquals(BigDecimal.valueOf(12.89), response.getResultadoSimulacao().get(0).getParcelas().get(1).getValorJuros());
            Assertions.assertEquals(BigDecimal.valueOf(192.89), response.getResultadoSimulacao().get(0).getParcelas().get(1).getValorPrestacao());
            Assertions.assertEquals(3, response.getResultadoSimulacao().get(0).getParcelas().get(2).getNumero());
            Assertions.assertEquals(BigDecimal.valueOf(180.00).setScale(2, RoundingMode.HALF_UP), response.getResultadoSimulacao().get(0).getParcelas().get(2).getValorAmortizacao());
            Assertions.assertEquals(BigDecimal.valueOf(9.67), response.getResultadoSimulacao().get(0).getParcelas().get(2).getValorJuros());
            Assertions.assertEquals(BigDecimal.valueOf(189.67), response.getResultadoSimulacao().get(0).getParcelas().get(2).getValorPrestacao());
            Assertions.assertEquals(4, response.getResultadoSimulacao().get(0).getParcelas().get(3).getNumero());
            Assertions.assertEquals(BigDecimal.valueOf(180.00).setScale(2, RoundingMode.HALF_UP), response.getResultadoSimulacao().get(0).getParcelas().get(3).getValorAmortizacao());
            Assertions.assertEquals(BigDecimal.valueOf(6.44), response.getResultadoSimulacao().get(0).getParcelas().get(3).getValorJuros());
            Assertions.assertEquals(BigDecimal.valueOf(186.44), response.getResultadoSimulacao().get(0).getParcelas().get(3).getValorPrestacao());
            Assertions.assertEquals(5, response.getResultadoSimulacao().get(0).getParcelas().get(4).getNumero());
            Assertions.assertEquals(BigDecimal.valueOf(180.00).setScale(2, RoundingMode.HALF_UP), response.getResultadoSimulacao().get(0).getParcelas().get(4).getValorAmortizacao());
            Assertions.assertEquals(BigDecimal.valueOf(3.22), response.getResultadoSimulacao().get(0).getParcelas().get(4).getValorJuros());
            Assertions.assertEquals(BigDecimal.valueOf(183.22), response.getResultadoSimulacao().get(0).getParcelas().get(4).getValorPrestacao());

        Assertions.assertEquals("PRICE", response.getResultadoSimulacao().get(1).getTipo());
            Assertions.assertEquals(1, response.getResultadoSimulacao().get(1).getParcelas().get(0).getNumero());
            Assertions.assertEquals(BigDecimal.valueOf(173.67), response.getResultadoSimulacao().get(1).getParcelas().get(0).getValorAmortizacao());
            Assertions.assertEquals(BigDecimal.valueOf(16.11), response.getResultadoSimulacao().get(1).getParcelas().get(0).getValorJuros());
            Assertions.assertEquals(BigDecimal.valueOf(189.78), response.getResultadoSimulacao().get(1).getParcelas().get(0).getValorPrestacao());
            Assertions.assertEquals(2, response.getResultadoSimulacao().get(1).getParcelas().get(1).getNumero());
            Assertions.assertEquals(BigDecimal.valueOf(176.78), response.getResultadoSimulacao().get(1).getParcelas().get(1).getValorAmortizacao());
            Assertions.assertEquals(BigDecimal.valueOf(13.00).setScale(2, RoundingMode.HALF_UP), response.getResultadoSimulacao().get(1).getParcelas().get(1).getValorJuros());
            Assertions.assertEquals(BigDecimal.valueOf(189.78), response.getResultadoSimulacao().get(1).getParcelas().get(1).getValorPrestacao());
            Assertions.assertEquals(3, response.getResultadoSimulacao().get(1).getParcelas().get(2).getNumero());
            Assertions.assertEquals(BigDecimal.valueOf(179.94), response.getResultadoSimulacao().get(1).getParcelas().get(2).getValorAmortizacao());
            Assertions.assertEquals(BigDecimal.valueOf(9.84), response.getResultadoSimulacao().get(1).getParcelas().get(2).getValorJuros());
            Assertions.assertEquals(BigDecimal.valueOf(189.78), response.getResultadoSimulacao().get(1).getParcelas().get(2).getValorPrestacao());
            Assertions.assertEquals(4, response.getResultadoSimulacao().get(1).getParcelas().get(3).getNumero());
            Assertions.assertEquals(BigDecimal.valueOf(183.16), response.getResultadoSimulacao().get(1).getParcelas().get(3).getValorAmortizacao());
            Assertions.assertEquals(BigDecimal.valueOf(6.62), response.getResultadoSimulacao().get(1).getParcelas().get(3).getValorJuros());
            Assertions.assertEquals(BigDecimal.valueOf(189.78), response.getResultadoSimulacao().get(1).getParcelas().get(3).getValorPrestacao());
            Assertions.assertEquals(5, response.getResultadoSimulacao().get(1).getParcelas().get(4).getNumero());
            Assertions.assertEquals(BigDecimal.valueOf(186.44), response.getResultadoSimulacao().get(1).getParcelas().get(4).getValorAmortizacao());
            Assertions.assertEquals(BigDecimal.valueOf(3.34), response.getResultadoSimulacao().get(1).getParcelas().get(4).getValorJuros());
            Assertions.assertEquals(BigDecimal.valueOf(189.78), response.getResultadoSimulacao().get(1).getParcelas().get(4).getValorPrestacao());
    }
}