package com.fernandocanabarro.oak_desafio.controller.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fernandocanabarro.oak_desafio.services.exceptions.BadRequestException;
import com.fernandocanabarro.oak_desafio.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public String badRequest(BadRequestException ex,Model model) {
        model.addAttribute("message", ex.getMessage());
        return "/error/bad-request";
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public String notFound(ResourceNotFoundException ex,Model model) {
        model.addAttribute("message", ex.getMessage());
        return "/error/not-found";
    }
}
