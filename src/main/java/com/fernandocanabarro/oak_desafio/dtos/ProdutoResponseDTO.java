package com.fernandocanabarro.oak_desafio.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdutoResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
    private String valor;
    private Boolean disponivel;
    private String imagem;
}
