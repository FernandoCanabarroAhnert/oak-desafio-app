package com.fernandocanabarro.oak_desafio.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fernandocanabarro.oak_desafio.models.PasswordRecover;

@Repository
public interface PasswordRecoverRepository extends JpaRepository<PasswordRecover,Long>{

    Optional<PasswordRecover> findByCode(String code);
}
