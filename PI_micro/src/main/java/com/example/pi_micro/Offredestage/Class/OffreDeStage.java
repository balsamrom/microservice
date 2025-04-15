package com.example.pi_micro.Offredestage.Class;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "offredestage")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OffreDeStage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // H2 ID (Long)

    @Column(nullable = false)
    private String titre;

    @Column(nullable = false, length = 1000)
    private String description;

    private String domaine;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    private int duree;

    private String typeStage;

    @Enumerated(EnumType.STRING)
    private Statut statut;
}
