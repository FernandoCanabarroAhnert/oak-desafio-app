package com.fernandocanabarro.oak_desafio.dtos;

import com.fernandocanabarro.oak_desafio.services.validations.ActivateAccountDTOValid;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ActivateAccountDTOValid
public class ActivateAccountDTO {

    @NotBlank(message = "Campo Requerido")
    private String code;
}
