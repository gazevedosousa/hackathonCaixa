package com.hackathon.simulacao.model.repository;

import com.hackathon.simulacao.model.entity.Produto;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ProdutoRepositoryTest {
    @Autowired
    ProdutoRepository produtoRepository;

    @Test
    public void buscaProdutoPorValor() {
        Produto produto1 = criaProduto1();
        Produto produto2 = criaProduto2();

        List<Produto> produtoList =  produtoRepository.findByVrMinimoLessThanEqualOrderByVrMinimoDesc(BigDecimal.valueOf(50000));

        Assertions.assertEquals(2, produtoList.size());
        Assertions.assertEquals(produto2.getName(), produtoList.get(0).getName());
        Assertions.assertEquals(produto1.getName(), produtoList.get(1).getName());
    }

    private Produto criaProduto1(){
        return Produto.builder()
                .id(1L)
                .name("Produto 1")
                .txJuros(BigDecimal.valueOf(0.017900000))
                .minMeses(0L)
                .maxMeses(24L)
                .vrMinimo(BigDecimal.valueOf(200.00))
                .vrMaximo(BigDecimal.valueOf(10000.00))
                .build();
    }

    private Produto criaProduto2(){
        return Produto.builder()
                .id(2L)
                .name("Produto 2")
                .txJuros(BigDecimal.valueOf(0.017500000))
                .minMeses(25L)
                .maxMeses(48L)
                .vrMinimo(BigDecimal.valueOf(10001.00))
                .vrMaximo(BigDecimal.valueOf(100000.00))
                .build();
    }
}