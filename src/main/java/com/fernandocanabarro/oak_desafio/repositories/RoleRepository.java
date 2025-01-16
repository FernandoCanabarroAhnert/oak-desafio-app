package com.fernandocanabarro.oak_desafio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fernandocanabarro.oak_desafio.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long>{

    Role findByAuthority(String authority);
}
