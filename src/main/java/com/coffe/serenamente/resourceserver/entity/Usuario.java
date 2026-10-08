package com.coffe.serenamente.resourceserver.entity;

import com.coffe.serenamente.resourceserver.entity.enuns.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", length = 255)
    private String nome;

    @Column(name = "email", length = 255, unique = true)
    private String email;

    @Column(name = "senhaCriptografada",length = 255)
    private String senhaCriptografada;

    @Column(name = "role", length = 20)
    private Role role;

    @Column(name = "status",length = 20)
    private String status;

    @Column(name = "emailVerificadoEm",columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime emailVerificadoEm;

    @CreationTimestamp
    @Column(name = "criadoEm",columnDefinition = "TIMESTAMPTZ", updatable = false)
    private OffsetDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizadoEm",columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime atualizadoEm;

    @Column(name = "excluidoEm",columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime excluidoEm;




}
