package com.fernandocanabarro.oak_desafio.services;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fernandocanabarro.oak_desafio.dtos.ProductUpdateDTO;
import com.fernandocanabarro.oak_desafio.dtos.ProdutoRequestDTO;
import com.fernandocanabarro.oak_desafio.dtos.ProdutoResponseDTO;

public interface ProdutoService {

    Page<ProdutoResponseDTO> listarProdutosPaginados(Pageable pageable);
    List<ProdutoResponseDTO> listarProdutos();
    List<ProdutoResponseDTO> listarPorNome(String nome);

    // List<ProdutoResponseDTO> listarPorValorAscendente();
    // List<ProdutoResponseDTO> listarPorValorDecrescente();

    ProductUpdateDTO encontrarProdutoParaUpdate(Long id);
    void adicionarProduto(ProdutoRequestDTO dto) throws IOException;
    void atualizarProduto(Long id,ProductUpdateDTO dto) throws IOException;
    void deletarProduto(Long id);
}
