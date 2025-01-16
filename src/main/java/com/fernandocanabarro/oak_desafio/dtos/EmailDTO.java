package com.fernandocanabarro.oak_desafio.dtos;

import com.fernandocanabarro.oak_desafio.services.validations.EmailDTOValid;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EmailDTOValid
public class EmailDTO {

    @Pattern(regexp = "^[A-Za-z0-9+._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "E-mail deve estar em formato válido")
    private String email;
}
