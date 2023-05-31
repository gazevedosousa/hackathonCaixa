package com.hackathon.simulacao.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoSimulacaoDTO {
    private String tipo;
    private List<ParcelaDTO> parcelas;
}
