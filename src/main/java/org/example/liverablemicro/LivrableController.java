package org.example.liverablemicro;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/livrables")
public class LivrableController {

    private final LivrableService service;
    private final LivrableRepository livrableRepository;

    public LivrableController(LivrableService service, LivrableRepository livrableRepository) {
        this.service = service;
        this.livrableRepository = livrableRepository;
    }

    @Value("${upload.dir}")
    private String uploadDir;

    // Crée un répertoire d'upload si nécessaire
    private void createUploadDir() {
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    // Sauvegarde le fichier dans le répertoire d'upload et retourne son nom
    private String saveFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadLivrable(
            @RequestParam("nomEtudiant") String nomEtudiant,
            @RequestParam(value = "lancementStagePfeFile", required = false) MultipartFile lancementStagePfeFile,
            @RequestParam(value = "demandeConventionFile", required = false) MultipartFile demandeConventionFile,
            @RequestParam(value = "remisePlanTravailFile", required = false) MultipartFile remisePlanTravailFile,
            @RequestParam(value = "depotJournalDeBordFile", required = false) MultipartFile depotJournalDeBordFile,
            @RequestParam(value = "depotBilanVersion1File", required = false) MultipartFile depotBilanVersion1File,
            @RequestParam(value = "depotBilanVersion2File", required = false) MultipartFile depotBilanVersion2File,
            @RequestParam(value = "depotRapportVersion1File", required = false) MultipartFile depotRapportVersion1File,
            @RequestParam(value = "lancementRestitution2File", required = false) MultipartFile lancementRestitution2File,
            @RequestParam(value = "depotBilanVersion3File", required = false) MultipartFile depotBilanVersion3File,
            @RequestParam(value = "depotRapportVersion2File", required = false) MultipartFile depotRapportVersion2File,
            @RequestParam(value = "enquetePremierEmploiFile", required = false) MultipartFile enquetePremierEmploiFile) {

        // Créer le répertoire d'upload s'il n'existe pas
        createUploadDir();

        try {
            // Sauvegarder les fichiers uniquement s'ils ne sont pas vides
            String lancementStagePfeFileName = (lancementStagePfeFile != null && !lancementStagePfeFile.isEmpty()) ?
                    saveFile(lancementStagePfeFile) : null;
            String demandeConventionFileName = (demandeConventionFile != null && !demandeConventionFile.isEmpty()) ?
                    saveFile(demandeConventionFile) : null;
            String remisePlanTravailFileName = (remisePlanTravailFile != null && !remisePlanTravailFile.isEmpty()) ?
                    saveFile(remisePlanTravailFile) : null;
            String depotJournalDeBordFileName = (depotJournalDeBordFile != null && !depotJournalDeBordFile.isEmpty()) ?
                    saveFile(depotJournalDeBordFile) : null;
            String depotBilanVersion1FileName = (depotBilanVersion1File != null && !depotBilanVersion1File.isEmpty()) ?
                    saveFile(depotBilanVersion1File) : null;
            String depotBilanVersion2FileName = (depotBilanVersion2File != null && !depotBilanVersion2File.isEmpty()) ?
                    saveFile(depotBilanVersion2File) : null;
            String depotRapportVersion1FileName = (depotRapportVersion1File != null && !depotRapportVersion1File.isEmpty()) ?
                    saveFile(depotRapportVersion1File) : null;
            String lancementRestitution2FileName = (lancementRestitution2File != null && !lancementRestitution2File.isEmpty()) ?
                    saveFile(lancementRestitution2File) : null;
            String depotBilanVersion3FileName = (depotBilanVersion3File != null && !depotBilanVersion3File.isEmpty()) ?
                    saveFile(depotBilanVersion3File) : null;
            String depotRapportVersion2FileName = (depotRapportVersion2File != null && !depotRapportVersion2File.isEmpty()) ?
                    saveFile(depotRapportVersion2File) : null;
            String enquetePremierEmploiFileName = (enquetePremierEmploiFile != null && !enquetePremierEmploiFile.isEmpty()) ?
                    saveFile(enquetePremierEmploiFile) : null;

            // Créer un objet Livrable avec les informations et les noms de fichiers
            Livrable livrable = new Livrable(nomEtudiant, lancementStagePfeFileName, demandeConventionFileName,
                    remisePlanTravailFileName, depotJournalDeBordFileName, depotBilanVersion1FileName,
                    depotBilanVersion2FileName, depotRapportVersion1FileName, lancementRestitution2FileName,
                    depotBilanVersion3FileName, depotRapportVersion2FileName, enquetePremierEmploiFileName);

            // Sauvegarder l'objet Livrable dans la base de données
            livrableRepository.save(livrable);

            // Retourner une réponse réussie
            return ResponseEntity.ok("Livrable enregistré avec succès !");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Erreur lors de l'upload des fichiers.");
        }
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) {
        try {
            Path path = Paths.get(uploadDir + File.separator + fileName);
            byte[] fileContent = Files.readAllBytes(path);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                    .body(fileContent);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping
    public List<Livrable> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public Livrable update(@PathVariable Long id, @RequestBody Livrable updatedLivrable) {
        return service.update(id, updatedLivrable);
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

    @GetMapping("/search")
    public List<Livrable> searchLivrables(@RequestParam(required = false) String nomEtudiant)
                                        {
        return service.searchLivrables(nomEtudiant);
    }

    @GetMapping("/{id}/download-links")
    public List<String> getDownloadLinks(@PathVariable Long id) {
        Livrable livrable = service.getById(id);
        if (livrable == null) {
            return List.of("Livrable not found");
        }

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

    private String createDownloadLink(String filename) {
        if (filename != null && !filename.isEmpty()) {
            String fileNameWithoutPath = filename.contains("/") ?
                    filename.substring(filename.lastIndexOf("/") + 1) :
                    filename;
            return "http://localhost:8081/files/" + fileNameWithoutPath;
        }
        return null;
    }
}
