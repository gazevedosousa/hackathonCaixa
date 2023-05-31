package com.hackathon.simulacao.service;

import com.hackathon.simulacao.api.dto.ResponseDTO;
import com.hackathon.simulacao.exceptions.NotFoundException;
import com.hackathon.simulacao.service.impl.ProdutoServiceImpl;
import com.hackathon.simulacao.exceptions.RegraNegocioException;
import com.hackathon.simulacao.model.repository.ProdutoRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

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
        Assertions.assertEquals("PRICE", response.getResultadoSimulacao().get(1).getTipo());
    }
}