package com.fernandocanabarro.oak_desafio.projections;

public interface UserDetailsProjection {

    String getUsername();
    String getPassword();
    Boolean getActivated();
    Long getRoleId();
    String getAuthority();
}
