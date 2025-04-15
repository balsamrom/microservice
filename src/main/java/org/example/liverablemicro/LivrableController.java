package org.example.liverablemicro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/livrables")
@CrossOrigin(origins = "*")
public class LivrableController {

    @Autowired
    private LivrableService livrableService;

    @Autowired
    private LivrableRepository livrableRepository;

    @Autowired
    private EmailService emailService;

    // Upload a new livrable (file)
    @PostMapping("/upload")
    public ResponseEntity<Livrable> uploadLivrable(@RequestParam("type") TypeLivrable type, @RequestParam("file") MultipartFile file) {
        // Vérification si un livrable précédent existe du même type


        // Création du livrable et traitement du fichier
        Livrable newLivrable = new Livrable();
        newLivrable.setType(type);
        newLivrable.setFileName(file.getOriginalFilename());
        newLivrable.setStatut(StatutLivrable.EN_ATTENTE); // Statut initial

        // Sauvegarde du fichier (logique de stockage à définir)
        livrableRepository.save(newLivrable);

        return ResponseEntity.ok(newLivrable);
    }

    // Get all livrables
    @GetMapping
    public List<Livrable> getAll() {
        return livrableService.getAll();
    }

    // Delete a livrable
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws IOException {
        livrableService.delete(id);
    }

    // Test email sending
    @PostMapping("/test-email")
    public ResponseEntity<String> testMail() {
        try {
            emailService.sendEmail("to@example.com", "Test Subject", "This is a test email.");
            return ResponseEntity.ok("Email sent successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error sending email: " + e.getMessage());
        }
    }

    // Update livrable status
    @PutMapping("/{id}/statut")
    public ResponseEntity<Livrable> updateStatut(@PathVariable Long id, @RequestParam("statut") String statut) {
        try {
            // Convertir la chaîne en valeur de l'énumération StatutLivrable
            StatutLivrable statutEnum = StatutLivrable.valueOf(statut.toUpperCase());

            Livrable updatedLivrable = livrableService.updateStatut(id, statutEnum);

            if (updatedLivrable == null) {
                return ResponseEntity.notFound().build();  // Retourne 404 si le livrable n'a pas été trouvé
            }

            return ResponseEntity.ok(updatedLivrable);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);  // Retourne 400 si le statut est invalide
        }
    }

    // Validate statut value
    private boolean isValidStatut(StatutLivrable statut) {
        try {
            StatutLivrable.valueOf(String.valueOf(statut));  // Vérifie si le statut est valide
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
