package com.fernandocanabarro.oak_desafio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.fernandocanabarro.oak_desafio.dtos.ActivateAccountDTO;
import com.fernandocanabarro.oak_desafio.dtos.EmailDTO;
import com.fernandocanabarro.oak_desafio.dtos.PasswordRecoverDTO;
import com.fernandocanabarro.oak_desafio.dtos.RegistrationRequestDTO;
import com.fernandocanabarro.oak_desafio.services.AuthService;

import jakarta.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String loginForm(Model model){
        return "auth/login-form";
    }

    @GetMapping("/register")
    public String registerForm(Model model){
        RegistrationRequestDTO registrationRequestDTO = new RegistrationRequestDTO();
        model.addAttribute("register", registrationRequestDTO);
        return "auth/register-form";
    }

    @PostMapping("/auth/register")
    public String register(@Valid @ModelAttribute("register") RegistrationRequestDTO register, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("register", register);
            return "auth/register-form";
        }
        authService.register(register);
        return "redirect:/activate-account";
    }

    @GetMapping("/activate-account")
    public String activateAccountForm(Model model){
        ActivateAccountDTO activateAccountDTO = new ActivateAccountDTO();
        model.addAttribute("activateDto", activateAccountDTO);
        return "auth/activate-account";
    }

    @PostMapping("/auth/activate-account")
    public String activateAccount(@Valid @ModelAttribute("activateDto") ActivateAccountDTO activateAccountDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("activateDto", activateAccountDTO);
            return "auth/activate-account";
        }
        authService.activateAccount(activateAccountDTO);
        return "redirect:/";
    }

    @GetMapping("/password-recover")
    public String forgotPasswordForm(Model model){
        EmailDTO emailDTO = new EmailDTO();
        model.addAttribute("emailDto", emailDTO);
        return "auth/forgot-password-form";
    }

    @PostMapping("/auth/password-recover")
    public String savePasswordRecover(@Valid @ModelAttribute("emailDto") EmailDTO emailDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("emailDto", emailDTO);
            return "auth/forgot-password-form";
        }
        authService.createPasswordRecover(emailDTO);
        return "redirect:/new-password";
    }

    @GetMapping("/new-password")
    public String newPasswordForm(Model model){
        PasswordRecoverDTO passwordRecoverDTO = new PasswordRecoverDTO();
        model.addAttribute("passwordRecoverDto", passwordRecoverDTO);
        return "auth/new-password-form";
    }

    @PostMapping("/auth/new-password")
    public String saveNewPassword(@Valid @ModelAttribute("passwordRecoverDto") PasswordRecoverDTO passwordRecoverDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("passwordRecoverDto", passwordRecoverDTO);
            return "auth/new-password-form";
        }
        authService.saveNewPassword(passwordRecoverDTO);
        return "redirect:/";
    }
}
