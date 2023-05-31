package com.hackathon.simulacao.api.controllers;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hackathon.simulacao.api.dto.ResponseDTO;
import com.hackathon.simulacao.service.ProdutoService;
import com.hackathon.simulacao.api.dto.RequestDTO;
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

    @PostMapping(value = "/buscar", produces = { "application/json" })
    public ResponseEntity buscar(@RequestBody RequestDTO dto){

        ResponseDTO response = service.buscarProdutoPorValor(dto.getValorDesejado(), dto.getPrazo());
        return new ResponseEntity(response, HttpStatus.OK);

    }

}