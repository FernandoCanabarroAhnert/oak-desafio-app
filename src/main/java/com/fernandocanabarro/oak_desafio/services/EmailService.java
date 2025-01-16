package com.fernandocanabarro.oak_desafio.services;

import com.sendgrid.helpers.mail.Mail;

public interface EmailService {

    void sendEmail(Mail mail);

    Mail createConfirmationEmailTemplate(String username,String email, String code);

    Mail createPasswordRecoverTemplate(String username,String email, String code);
}
