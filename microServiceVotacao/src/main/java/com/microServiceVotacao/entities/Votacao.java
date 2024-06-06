package com.microServiceVotacao.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "votacao")
public class Votacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idProposta;
    private Integer votosPositivos;
    private Integer votosContras;
    private Boolean ativa;
}
