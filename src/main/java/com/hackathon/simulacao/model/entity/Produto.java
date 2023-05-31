package com.hackathon.simulacao.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name="Produto")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Produto {
    @Id
    @Column(name = "CO_PRODUTO")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, name = "NO_PRODUTO")
    private String name;

    @Column(nullable = false, name = "PC_TAXA_JUROS")
    private BigDecimal txJuros;

    @Column(nullable = false, name = "NU_MINIMO_MESES")
    private Long minMeses;

    @Column(name = "NU_MAXIMO_MESES")
    private Long maxMeses;

    @Column(nullable = false, name = "VR_MINIMO")
    private BigDecimal vrMinimo;

    @Column(name = "VR_MAXIMO")
    private BigDecimal vrMaximo;
}
