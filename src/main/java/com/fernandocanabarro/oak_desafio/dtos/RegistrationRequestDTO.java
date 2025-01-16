package com.fernandocanabarro.oak_desafio.dtos;

import com.fernandocanabarro.oak_desafio.services.validations.RegistrationRequestDTOValid;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RegistrationRequestDTOValid
public class RegistrationRequestDTO {

    @NotBlank(message = "Campo requerido")
    private String fullName;
    @Pattern(regexp = "^[A-Za-z0-9+._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "E-mail deve estar em formato válido")
    private String email;
    @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
    private String password;
    @Size(min = 8,message = "Senha deve ter no mínimo 8 caracteres")
    private String passwordAck;
}
