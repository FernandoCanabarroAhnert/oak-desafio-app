package com.fernandocanabarro.oak_desafio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fernandocanabarro.oak_desafio.models.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto,Long>{

    List<Produto> findByNomeContainingIgnoreCase(String nome);

    @Query(nativeQuery = true, value = """
            SELECT * FROM produtos
            ORDER BY valor ASC
            """)
    List<Produto> ordenarProdutosPorValorAsc();

    @Query(nativeQuery = true, value = """
            SELECT * FROM produtos
            ORDER BY valor DESC
            """)
    List<Produto> ordenarProdutosPorValorDesc();
}
