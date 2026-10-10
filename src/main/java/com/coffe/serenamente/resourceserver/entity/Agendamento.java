package com.coffe.serenamente.resourceserver.entity;

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
@Table(name = "Agendamento")
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID agendamentoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Profissional profissional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "horario_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private HorarioDisponivel horario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "preco_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private PrecoProfissional preco;

    @Column(name = "agendado_para", columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime agendadoPara;

    @Column(name = "status", length = 30)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cancelado_por")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Usuario canceladoPor;

    @Column(name = "motivo_cancelamento", columnDefinition = "TEXT")
    private String motivoCancelamento;

    @CreationTimestamp
    @Column(name = "criado_em", columnDefinition = "TIMESTAMPTZ", updatable = false)
    private OffsetDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em", columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime atualizadoEm;

}
