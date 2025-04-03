package org.example.liverablemicro;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/livrables")
public class LivrableController {

    private final LivrableService service;

    public LivrableController(LivrableService service) {
        this.service = service;
    }

    @GetMapping
    public List<Livrable> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Livrable getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/mail")
    public Livrable mail(@RequestBody Livrable livrable) {
        service.mail(livrable);
        return livrable;
    }

    @PostMapping
    public Livrable create(@RequestBody Livrable livrable) {
        return service.save(livrable);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/{id}/download-links")
    public List<String> getDownloadLinks(@PathVariable Long id) {
        Livrable livrable = service.getById(id);
        if (livrable == null) {
            return List.of("Livrable not found");
        }

        // Construire les URL de téléchargement pour chaque fichier
        return List.of(
                createDownloadLink(livrable.getLancementStagePfeFile()),
                createDownloadLink(livrable.getDemandeConventionFile()),
                createDownloadLink(livrable.getRemisePlanTravailFile()),
                createDownloadLink(livrable.getDepotJournalDeBordFile()),
                createDownloadLink(livrable.getDepotBilanVersion1File()),
                createDownloadLink(livrable.getDepotBilanVersion2File()),
                createDownloadLink(livrable.getDepotRapportVersion1File()),
                createDownloadLink(livrable.getLancementRestitution2File()),
                createDownloadLink(livrable.getDepotBilanVersion3File()),
                createDownloadLink(livrable.getDepotRapportVersion2File()),
                createDownloadLink(livrable.getEnquetePremierEmploiFile())
        ).stream().filter(link -> link != null).collect(Collectors.toList());
    }

    // Méthode pour créer un lien de téléchargement pour chaque fichier
    private String createDownloadLink(String filename) {
        if (filename != null && !filename.isEmpty()) {
            String fileNameWithoutPath = filename.substring(filename.lastIndexOf("\\") + 1);
            return "http://localhost:8081/files/" + fileNameWithoutPath;
        }
        return null;
    }
}
