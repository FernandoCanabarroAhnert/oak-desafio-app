package com.fernandocanabarro.oak_desafio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fernandocanabarro.oak_desafio.models.ImagemProduto;

@Repository
public interface ImagemProdutoRepository extends JpaRepository<ImagemProduto,Long>{

}
