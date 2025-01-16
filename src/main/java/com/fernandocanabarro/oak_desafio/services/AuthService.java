package com.fernandocanabarro.oak_desafio.services;

import com.fernandocanabarro.oak_desafio.dtos.ActivateAccountDTO;
import com.fernandocanabarro.oak_desafio.dtos.EmailDTO;
import com.fernandocanabarro.oak_desafio.dtos.PasswordRecoverDTO;
import com.fernandocanabarro.oak_desafio.dtos.RegistrationRequestDTO;
import com.fernandocanabarro.oak_desafio.models.User;

public interface AuthService {

    void register(RegistrationRequestDTO dto);

    void sendConfirmationEmail(User user);

    String createActivationCodeAndSave(User user);

    String createCode();

    void activateAccount(ActivateAccountDTO dto);

    void createPasswordRecover(EmailDTO dto);

    void saveNewPassword(PasswordRecoverDTO dto);
}
