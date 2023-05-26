package com.hackaton.simulacao.api.controllers;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hackaton.simulacao.api.dto.RequestDTO;
import com.hackaton.simulacao.model.entity.Produto;
import com.hackaton.simulacao.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/produto")
@JsonDeserialize
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService service;

    @PostMapping(value = "/buscar")
    public ResponseEntity buscar(@RequestBody RequestDTO dto){

        Produto produto = service.buscarProdutoPorValor(dto.getValorDesejado());
        return new ResponseEntity(produto, HttpStatus.OK);

    }

}