
package org.example.liverablemicro;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.*;
        import java.util.stream.Collectors;

@Service
public class LivrableService {

    private final LivrableRepository repository;
    private final EmailService emailService;

    public LivrableService(LivrableRepository repository, EmailService emailService) {
        this.repository = repository;
        this.emailService = emailService;
    }

    public List<Livrable> getAll() {
        return repository.findAll();
    }

    public Livrable getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Livrable save(Livrable livrable) {
        Livrable savedLivrable = repository.save(livrable);

        // Génère le PDF de confirmation
        File pdf = generateLivrablePDF(savedLivrable);

        // Envoie un mail avec la pièce jointe PDF
        emailService.sendEmailWithAttachment(
                "balsam.romdhane17@gmail.com",
                "✅ Livrable ajouté",
                "Le livrable a été enregistré avec succès. Voir le document joint pour les détails.",
                pdf
        );

        return savedLivrable;
    }

    public File generateLivrablePDF(Livrable livrable) {
        String filePath = "livrable_" + livrable.getId() + ".pdf";
        File file = new File(filePath);

        try {
            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("📌 Confirmation de dépôt de livrable"));
            document.add(new Paragraph("👤 Étudiant : " + livrable.getNomEtudiant()));
            document.add(new Paragraph("🗓 Date de dépôt : " + LocalDateTime.now()));

            document.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return file;
    }
    public void mail(Livrable livrable) {
        // Envoyer un email lors de l'ajout d'un livrable
        emailService.sendEmail("balsam.romdhane17@gmail.com", "Livrable envoyé", "Le livrable a été envoyé par email");
    }

    // Méthode de recherche et de tri
    public List<Livrable> searchLivrables(String nomEtudiant) {
        // Récupération de tous les livrables
        List<Livrable> livrables = repository.findAll();

        // Filtrage par nom de l'étudiant (si nomEtudiant est spécifié)
        if (nomEtudiant != null && !nomEtudiant.isEmpty()) {
            livrables = livrables.stream()
                    .filter(livrable -> livrable.getNomEtudiant().toLowerCase().contains(nomEtudiant.toLowerCase()))
                    .collect(Collectors.toList());
        }

//        // Filtrage par type de fichier (si fileType est spécifié)
//        if (fileType != null && !fileType.isEmpty()) {
//            livrables = livrables.stream()
//                    .filter(livrable -> {
//                        switch (fileType) {
//                            case "demande_convention_file":
//                                return livrable.getDemandeConventionFile() != null;
//                            case "depot_bilan_version1file":
//                                return livrable.getDepotBilanVersion1File() != null;
//                            case "depot_bilan_version2file":
//                                return livrable.getDepotBilanVersion2File() != null;
//                            case "depot_bilan_version3file":
//                                return livrable.getDepotBilanVersion3File() != null;
//                            case "depot_journal_de_bord_file":
//                                return livrable.getDepotJournalDeBordFile() != null;
//                            case "depot_rapport_version1file":
//                                return livrable.getDepotRapportVersion1File() != null;
//                            case "depot_rapport_version2file":
//                                return livrable.getDepotRapportVersion2File() != null;
//                            case "enquete_premier_emploi_file":
//                                return livrable.getEnquetePremierEmploiFile() != null;
//                            case "lancement_restitution2file":
//                                return livrable.getLancementRestitution2File() != null;
//                            case "lancement_stage_pfe_file":
//                                return livrable.getLancementStagePfeFile() != null;
//                            case "remise_plan_travail_file":
//                                return livrable.getRemisePlanTravailFile() != null;
//                            default:
//                                return false;
//                        }
//                    })
//                    .collect(Collectors.toList());
//        }

        // Tri des résultats
//        if ("nomEtudiant".equals(sortBy)) {
//            livrables.sort(Comparator.comparing(Livrable::getNomEtudiant));
//        } else if ("id".equals(sortBy)) {
//            livrables.sort(Comparator.comparing(Livrable::getId));
//        }
//
//        // Tri par ordre (ascendant ou descendant)
//        if ("desc".equals(sortOrder)) {
//            Collections.reverse(livrables);
//        }

        return livrables;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    // Méthode de mise à jour
    public Livrable update(Long id, Livrable updatedLivrable) {
        Livrable existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livrable not found"));

        // Mise à jour des champs nécessaires
        existing.setLancementStagePfeFile(updatedLivrable.getLancementStagePfeFile());
        existing.setDemandeConventionFile(updatedLivrable.getDemandeConventionFile());
        existing.setRemisePlanTravailFile(updatedLivrable.getRemisePlanTravailFile());
        existing.setDepotJournalDeBordFile(updatedLivrable.getDepotJournalDeBordFile());
        existing.setDepotBilanVersion1File(updatedLivrable.getDepotBilanVersion1File());
        existing.setDepotBilanVersion2File(updatedLivrable.getDepotBilanVersion2File());
        existing.setDepotRapportVersion1File(updatedLivrable.getDepotRapportVersion1File());
        existing.setLancementRestitution2File(updatedLivrable.getLancementRestitution2File());
        existing.setDepotBilanVersion3File(updatedLivrable.getDepotBilanVersion3File());
        existing.setDepotRapportVersion2File(updatedLivrable.getDepotRapportVersion2File());
        existing.setEnquetePremierEmploiFile(updatedLivrable.getEnquetePremierEmploiFile());

        // Enregistre la mise à jour
        return repository.save(existing);
    }

}
