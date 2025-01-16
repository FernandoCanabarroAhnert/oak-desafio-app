package com.fernandocanabarro.oak_desafio.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "password_recovers")
public class PasswordRecover {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    private LocalDateTime expiresAt;

    private LocalDateTime validatedAt;

    public PasswordRecover(String code, User user){
        this.code = code;
        this.user = user;
        this.expiresAt = LocalDateTime.now().plusMinutes(30L);
    }

    public boolean isValid(){
        return this.expiresAt.isBefore(LocalDateTime.now().plusMinutes(30L));
    }
}
