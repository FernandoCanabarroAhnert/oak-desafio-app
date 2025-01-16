package com.fernandocanabarro.oak_desafio.dtos;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
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
public class ProdutoRequestDTO {

    @NotBlank(message = "Nome não pode ficar vazio")
    private String nome;
    @NotBlank(message = "Descrição não pode ficar vazia")
    private String descricao;
    private String valor;
    private Boolean disponivel;
    private MultipartFile imagem;
}
