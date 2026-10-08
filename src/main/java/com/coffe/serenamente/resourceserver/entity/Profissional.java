package com.coffe.serenamente.resourceserver.entity;

import com.coffe.serenamente.resourceserver.entity.enuns.TipoLicensa;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "profissionais")
public class Profissional {

    @Id
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Usuario usuario;

    @Column(name = "numero_licensa", length = 30)
    private String numerolicensa;

    @Column(name = "tipo_licensa", length = 10)
    private TipoLicensa tipolicensa;

    @Column(name = "especialidade", length = 100)
    private String especialidade;

    @Column(name = "abordagem", length = 150)
    private String abordagem;

    @Column(name = "bio", length = 255)
    private String bio;

    @Column(name = "status_verificacao", length = 20)
    private String statusVerificacao;

    @CreationTimestamp
    @Column(name = "criado_em", columnDefinition = "TIMESTAMPTZ", updatable = false)
    private OffsetDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em", columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime atualizadoEm;

    @Column(name = "verificado_em", columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime verificadoEm;




}
