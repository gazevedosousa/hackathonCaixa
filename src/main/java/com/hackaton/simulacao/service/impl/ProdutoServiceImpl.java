package com.hackaton.simulacao.service.impl;

import com.hackaton.simulacao.api.dto.ParcelaDTO;
import com.hackaton.simulacao.api.dto.ResponseDTO;
import com.hackaton.simulacao.api.dto.ResultadoSimulacaoDTO;
import com.hackaton.simulacao.exceptions.RegraNegocioException;
import com.hackaton.simulacao.model.entity.Produto;
import com.hackaton.simulacao.model.repository.ProdutoRepository;
import com.hackaton.simulacao.service.ProdutoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository repository;

    @Override
    public ResponseDTO buscarProdutoPorValor(BigDecimal valorDesejado, int prazo) throws RegraNegocioException {
        List<Produto> produtosList = repository.buscaProdutoPorValor((valorDesejado));

        if(produtosList.isEmpty()){
            throw new RegraNegocioException("Valor desejado inferior ao mínimo de R$200,00");
        }

        Produto produto =  produtosList.get(0);

        if(prazo < produto.getNU_MINIMO_MESES()){
            throw new RegraNegocioException("Prazo menor que o mínimo necessário para a faixa de valor solicitado");
        }

        BigDecimal taxaJuros = produto.getPC_TAXA_JUROS();

        ResultadoSimulacaoDTO calculoPrice = calculaPrice(taxaJuros, prazo, valorDesejado);
        ResultadoSimulacaoDTO calculoSAC = calculaSAC(taxaJuros, prazo, valorDesejado);

        List<ResultadoSimulacaoDTO> resultadoSimulacaoDTOList = new ArrayList<>();

        resultadoSimulacaoDTOList.add(calculoSAC);
        resultadoSimulacaoDTOList.add(calculoPrice);

        return ResponseDTO.builder()
                .codigoProduto(produto.getCO_PRODUTO())
                .descricaoProduto(produto.getNO_PRODUTO())
                .taxaJuros(taxaJuros.setScale(4, RoundingMode.HALF_UP))
                .resultadoSimulacao(resultadoSimulacaoDTOList)
                .build();

    }

    @Override
    public ResultadoSimulacaoDTO calculaPrice(BigDecimal taxaJuros, int prazo, BigDecimal valorDesejado) {

        BigDecimal taxaJurosSomada = taxaJuros.add(new BigDecimal(1));
        BigDecimal numerador = taxaJurosSomada.pow(prazo).multiply(taxaJuros);
        BigDecimal denominador = taxaJurosSomada.pow(prazo).subtract(new BigDecimal(1));
        BigDecimal parcelaPrice = numerador.divide(denominador, 20, RoundingMode.HALF_UP).multiply(valorDesejado);
        BigDecimal saldoRestante = valorDesejado;

        List<ParcelaDTO> parcelaDTOList = new ArrayList<>();

        for(int numero = 1; numero <= prazo; numero++){

            BigDecimal vrAtualizado = taxaJurosSomada.multiply(saldoRestante);
            BigDecimal juros = vrAtualizado.subtract(saldoRestante);
            BigDecimal amortizacao = parcelaPrice.subtract(juros);
            saldoRestante = saldoRestante.subtract(amortizacao);

            ParcelaDTO parcela = ParcelaDTO.builder()
                    .numero(numero)
                    .valorAmortizacao(amortizacao.setScale(2, RoundingMode.HALF_UP))
                    .valorJuros(juros.setScale(2, RoundingMode.HALF_UP))
                    .valorPrestacao(parcelaPrice.setScale(2, RoundingMode.HALF_UP))
                    .build();

            parcelaDTOList.add(parcela);
        }

        return ResultadoSimulacaoDTO.builder()
                .tipo("PRICE")
                .parcelas(parcelaDTOList)
                .build();
    }

    @Override
    public ResultadoSimulacaoDTO calculaSAC(BigDecimal taxaJuros, int prazo, BigDecimal valorDesejado) {
        BigDecimal taxaJurosSomada = taxaJuros.add(new BigDecimal(1));
        BigDecimal saldoRestante = valorDesejado;
        BigDecimal valorAmortizacaoSAC = valorDesejado.divide(BigDecimal.valueOf(prazo), 20, RoundingMode.HALF_UP);

        List<ParcelaDTO> parcelaDTOList = new ArrayList<>();

        for(int numero = 1; numero <= prazo; numero++){
            BigDecimal vrAtualizado = taxaJurosSomada.multiply(saldoRestante);
            BigDecimal juros = vrAtualizado.subtract(saldoRestante);
            BigDecimal valorPrestacao = valorAmortizacaoSAC.add(juros);
            saldoRestante = saldoRestante.subtract(valorAmortizacaoSAC);

            ParcelaDTO parcela = ParcelaDTO.builder()
                    .numero(numero)
                    .valorAmortizacao(valorAmortizacaoSAC.setScale(2, RoundingMode.HALF_UP))
                    .valorJuros(juros.setScale(2, RoundingMode.HALF_UP))
                    .valorPrestacao(valorPrestacao.setScale(2, RoundingMode.HALF_UP))
                    .build();

            parcelaDTOList.add(parcela);
        }

        return ResultadoSimulacaoDTO.builder()
                .tipo("SAC")
                .parcelas(parcelaDTOList)
                .build();
    }
}
