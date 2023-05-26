package com.hackaton.simulacao.api.controllers;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hackaton.simulacao.api.dto.ParcelaDTO;
import com.hackaton.simulacao.api.dto.RequestDTO;
import com.hackaton.simulacao.api.dto.ResponseDTO;
import com.hackaton.simulacao.api.dto.ResultadoSimulacaoDTO;
import com.hackaton.simulacao.model.entity.Produto;
import com.hackaton.simulacao.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/produto")
@JsonDeserialize
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService service;

    @PostMapping(value = "/buscar")
    public ResponseEntity buscar(@RequestBody RequestDTO dto){

        ResponseDTO response = service.buscarProdutoPorValor(dto.getValorDesejado(), dto.getPrazo());
        return new ResponseEntity(response, HttpStatus.OK);

    }

}