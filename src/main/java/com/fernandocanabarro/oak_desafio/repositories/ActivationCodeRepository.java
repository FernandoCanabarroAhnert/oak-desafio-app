package com.fernandocanabarro.oak_desafio.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fernandocanabarro.oak_desafio.models.ActivationCode;

@Repository
public interface ActivationCodeRepository extends JpaRepository<ActivationCode,Long>{

    Optional<ActivationCode> findByCode(String code);
}
