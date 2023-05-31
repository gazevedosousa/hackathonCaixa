package com.hackathon.simulacao.service.impl;

import com.hackathon.simulacao.exceptions.NotFoundException;
import com.hackathon.simulacao.service.ProdutoService;
import com.hackathon.simulacao.api.dto.ParcelaDTO;
import com.hackathon.simulacao.api.dto.ResponseDTO;
import com.hackathon.simulacao.api.dto.ResultadoSimulacaoDTO;
import com.hackathon.simulacao.exceptions.RegraNegocioException;
import com.hackathon.simulacao.model.entity.Produto;
import com.hackathon.simulacao.model.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.azure.messaging.eventhubs.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository repository;
    private static final String connectionString = "Endpoint=sb://eventhack.servicebus.windows.net/;SharedAccessKeyName=hack;SharedAccessKey=HeHeVaVqyVkntO2FnjQcs2Ilh/4MUDo4y+AEhKp8z+g=;EntityPath=simulacoes";
    private static final String eventHubName = "simulacoes";

    @Override
    public ResponseDTO buscarProdutoPorValor(BigDecimal valorDesejado, int prazo) throws RegraNegocioException, NotFoundException {
        List<Produto> produtosList = repository.findByVrMinimoLessThanEqualOrderByVrMinimoDesc(valorDesejado);

        if(produtosList.isEmpty()){
            throw new NotFoundException("Produto não encontrado para o valor desejado");
        }

        Produto produto =  produtosList.get(0);

        if(prazo < produto.getMinMeses() || prazo == 0){
            throw new RegraNegocioException("Prazo solicitado não compatível com o valor desejado.");
        }

        if(produto.getMaxMeses() != null && prazo > produto.getMaxMeses()){
            throw new RegraNegocioException("Prazo solicitado não compatível com o valor desejado.");
        }

        BigDecimal taxaJuros = produto.getTxJuros();

        ResultadoSimulacaoDTO calculoPrice = calculaPrice(taxaJuros, prazo, valorDesejado);
        ResultadoSimulacaoDTO calculoSAC = calculaSAC(taxaJuros, prazo, valorDesejado);

        List<ResultadoSimulacaoDTO> resultadoSimulacaoDTOList = new ArrayList<>();

        resultadoSimulacaoDTOList.add(calculoSAC);
        resultadoSimulacaoDTOList.add(calculoPrice);

        ResponseDTO response = ResponseDTO.builder()
                .codigoProduto(produto.getId())
                .descricaoProduto(produto.getName())
                .taxaJuros(taxaJuros.setScale(4, RoundingMode.HALF_UP))
                .resultadoSimulacao(resultadoSimulacaoDTOList)
                .build();

        enviaEventHub(response);

        return response;

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

    @Override
    public void enviaEventHub(ResponseDTO jsonSimulacao) {
        EventHubProducerClient producer = new EventHubClientBuilder()
                .connectionString(connectionString, eventHubName)
                .buildProducerClient();

        EventDataBatch eventDataBatch = producer.createBatch();

        eventDataBatch.tryAdd(new EventData(String.valueOf(jsonSimulacao)));

        producer.send(eventDataBatch);
    }
}
