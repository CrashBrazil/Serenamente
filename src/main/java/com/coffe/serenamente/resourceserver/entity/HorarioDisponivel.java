package com.coffe.serenamente.resourceserver.entity;


import com.coffe.serenamente.resourceserver.entity.enuns.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Horarios_Disponiveis")
public class HorarioDisponivel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID horarioDisponivelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Profissional profissional;

    @Column(name = "hora_inicio", columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime horaInicio;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @CreationTimestamp
    @Column(name = "criado_em", columnDefinition = "TIMESTAMPTZ", updatable = false)
    private OffsetDateTime criadoEm;
}
