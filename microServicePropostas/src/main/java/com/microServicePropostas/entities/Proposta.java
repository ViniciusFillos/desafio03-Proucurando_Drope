package com.microServicePropostas.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Data
@Getter
@EqualsAndHashCode
@Entity
@Table(name = "proposta")
public class Proposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;

}
