package com.hackaton.simulacao.api.dto;

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
public class ResutaldoSimulacaoDTO {
    private String tipo;
    private List<ParcelasDTO> parcelas;
}
