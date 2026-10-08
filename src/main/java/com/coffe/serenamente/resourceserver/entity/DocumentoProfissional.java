package com.coffe.serenamente.resourceserver.entity;

import com.coffe.serenamente.resourceserver.entity.enuns.Status;
import com.coffe.serenamente.resourceserver.entity.enuns.TipoDocumento;
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
@Table(name = "documentos_profissionais")
public class DocumentoProfissional {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID documentoProfissionalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Profissional profissional;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento", length = 50)
    private TipoDocumento tipoDocumento;

    @Column(name = "chave_armazenamento", columnDefinition = "TEXT")
    private String chaveArmazenamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "revisado_por")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Usuario revisadoPor;

    @Column(name = "revisado_em", columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime revisadoEm;

    @Column(name = "motivo_rejeicao", columnDefinition = "TEXT")
    private String motivoRejeicao;

    @CreationTimestamp
    @Column(name = "criado_em", columnDefinition = "TIMESTAMPTZ", updatable = false)
    private OffsetDateTime criadoEm;

}
