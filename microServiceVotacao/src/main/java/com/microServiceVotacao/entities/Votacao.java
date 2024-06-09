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

    @Column(nullable = false)
    private Long idProposta;

    @Column(nullable = false)
    private Integer votosPositivos;

    @Column(nullable = false)
    private Integer votosContras;
}
