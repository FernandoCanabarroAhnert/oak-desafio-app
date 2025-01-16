package com.fernandocanabarro.oak_desafio.mapper;

import java.math.BigDecimal;

import com.fernandocanabarro.oak_desafio.dtos.ProdutoRequestDTO;
import com.fernandocanabarro.oak_desafio.dtos.ProdutoResponseDTO;
import com.fernandocanabarro.oak_desafio.models.Produto;

public class ProdutoMapper {

    public static ProdutoResponseDTO mapEntityToDto(Produto entity){
        String valorProduto = String.valueOf(entity.getValor());
        valorProduto = valorProduto.replace(".", ",");
        return ProdutoResponseDTO.builder()
            .id(entity.getId())
            .nome(entity.getNome())
            .descricao(entity.getDescricao())
            .valor(valorProduto)
            .disponivel(entity.getDisponivel())
            .imagem(entity.getImagem().getImagemOriginal())
            .build();
    }

    public static Produto mapDtoToEntity(ProdutoRequestDTO dto){
        String valorProduto = dto.getValor().replace(",", ".");
        double valor = Double.parseDouble(valorProduto);
        return Produto.builder()
            .nome(dto.getNome())
            .descricao(dto.getDescricao())
            .valor(BigDecimal.valueOf(valor))
            .disponivel(dto.getDisponivel())
            .build();
    }
}
