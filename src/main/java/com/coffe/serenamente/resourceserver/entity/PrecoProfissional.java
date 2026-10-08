package com.coffe.serenamente.resourceserver.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "precos_profissionais")
public class PrecoProfissional {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID precoProfissionais;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profissional_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Profissional profissional;

    @Column(name = "nome", length = 100)
    private String nome;

    @Column(name = "faixa_de_renda", length = 50)
    private String faixaRenda;

    @Column(name = "valor", precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(name = "e_social")
    private Boolean e_social;

    @Column(name = "ativo")
    private Boolean ativo;

    @CreationTimestamp
    @Column(name = "criado_em", columnDefinition = "TIMESTAMPTZ", updatable = false)
    private OffsetDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em", columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime atualizadoEm;


}
