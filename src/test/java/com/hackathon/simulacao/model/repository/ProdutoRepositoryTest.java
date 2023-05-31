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
        Produto produto = criaProduto();

        List<Produto> produtoList =  produtoRepository.findByVrMinimoLessThanEqualOrderByVrMinimoDesc(BigDecimal.valueOf(1000));

        Assertions.assertEquals(1, produtoList.size());
        Assertions.assertEquals(produto.getName(), produtoList.get(0).getName());
    }

    private Produto criaProduto(){
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
}