package com.fernandocanabarro.oak_desafio.dtos;

import org.springframework.web.multipart.MultipartFile;

import com.fernandocanabarro.oak_desafio.models.Produto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateDTO {

    private Long id;
    @NotBlank(message = "Nome não pode ficar vazio")
    private String nome;
    @NotBlank(message = "Descrição não pode ficar vazia")
    private String descricao;
    private String valor;
    private Boolean disponivel;
    private MultipartFile imagem;

    public ProductUpdateDTO(Produto obj) {
        id = obj.getId();
        nome = obj.getNome();
        descricao = obj.getDescricao();
        valor = String.valueOf(obj.getValor()).replace(".", ",");
        disponivel = obj.getDisponivel();
    }
}
