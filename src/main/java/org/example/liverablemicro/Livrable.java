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
    public Livrable(String nomEtudiant, String lancementStagePfeFile, String demandeConventionFile,
                    String remisePlanTravailFile, String depotJournalDeBordFile, String depotBilanVersion1File,
                    String depotBilanVersion2File, String depotRapportVersion1File, String lancementRestitution2File,
                    String depotBilanVersion3File, String depotRapportVersion2File, String enquetePremierEmploiFile) {
        this.nomEtudiant = nomEtudiant;
        this.lancementStagePfeFile = lancementStagePfeFile;
        this.demandeConventionFile = demandeConventionFile;
        this.remisePlanTravailFile = remisePlanTravailFile;
        this.depotJournalDeBordFile = depotJournalDeBordFile;
        this.depotBilanVersion1File = depotBilanVersion1File;
        this.depotBilanVersion2File = depotBilanVersion2File;
        this.depotRapportVersion1File = depotRapportVersion1File;
        this.lancementRestitution2File = lancementRestitution2File;
        this.depotBilanVersion3File = depotBilanVersion3File;
        this.depotRapportVersion2File = depotRapportVersion2File;
        this.enquetePremierEmploiFile = enquetePremierEmploiFile;
    }
}
