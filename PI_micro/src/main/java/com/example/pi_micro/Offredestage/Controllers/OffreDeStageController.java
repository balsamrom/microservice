package com.example.pi_micro.Offredestage.Controllers;

import com.example.pi_micro.Offredestage.Class.OffreDeStage;
import com.example.pi_micro.Offredestage.Class.Statut;
import com.example.pi_micro.Offredestage.Services.OffreDeStageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/offres")
@RequiredArgsConstructor
@CrossOrigin("*") // Autoriser les requêtes du frontend
public class OffreDeStageController {
    private final OffreDeStageService offreDeStageService;

    // Créer ou mettre à jour une offre
    @PostMapping("/create")
    public OffreDeStage createOrUpdateOffre(@RequestBody OffreDeStage offreDeStage) {
        return offreDeStageService.saveOffre(offreDeStage);
    }
    @GetMapping("/search")
    public ResponseEntity<List<OffreDeStage>> searchOffres(
            @RequestParam(required = false) String titre,

            @RequestParam(required = false) Statut statut) {
        List<OffreDeStage> result = offreDeStageService.searchOffres(titre, statut);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        return ResponseEntity.ok(offreDeStageService.getStats());
    }

    // Mettre à jour le statut d'une offre
    @PatchMapping("/{id}/statut")
    public ResponseEntity<?> updateStatut(@PathVariable Long id, @RequestBody Statut statut) {
        try {
            if (statut == Statut.COMPLETED) {
                offreDeStageService.deleteOffre(id);
                return ResponseEntity.noContent().build();
            } else {
                OffreDeStage updatedOffre = offreDeStageService.updateStatut(id, statut);
                return ResponseEntity.ok(updatedOffre);
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Récupérer toutes les offres
    @GetMapping("/getall")
    public ResponseEntity<List<OffreDeStage>> getAllOffres() {
        List<OffreDeStage> offres = offreDeStageService.getAllOffres();
        return ResponseEntity.ok(offres);
    }

    // Récupérer une offre par ID
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getOffreById(@PathVariable Long id) {
        try {
            OffreDeStage offre = offreDeStageService.getOffreById(id);
            return ResponseEntity.ok(offre);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Supprimer une offre
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOffre(@PathVariable Long id) {
        try {
            offreDeStageService.deleteOffre(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
