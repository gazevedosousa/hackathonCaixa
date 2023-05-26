package com.hackaton.simulacao.model.entity;

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
    @Column
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long CO_PRODUTO;

    @Column(nullable = false)
    private String NO_PRODUTO;

    @Column(nullable = false)
    private BigDecimal PC_TAXA_JUROS;

    @Column(nullable = false)
    private Long NU_MINIMO_MESES;

    @Column
    private Long NU_MAXIMO_MESES;

    @Column(nullable = false)
    private BigDecimal VR_MINIMO;

    @Column
    private BigDecimal VR_MAXIMO;
}
