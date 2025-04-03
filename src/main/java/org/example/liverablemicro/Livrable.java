package org.example.liverablemicro;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "livrables")
public class Livrable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomEtudiant;

    private String lancementStagePfeFile;
    private String demandeConventionFile;
    private String remisePlanTravailFile;
    private String depotJournalDeBordFile;
    private String depotBilanVersion1File;
    private String depotBilanVersion2File;
    private String depotRapportVersion1File;
    private String lancementRestitution2File;
    private String depotBilanVersion3File;
    private String depotRapportVersion2File;
    private String enquetePremierEmploiFile;
}
