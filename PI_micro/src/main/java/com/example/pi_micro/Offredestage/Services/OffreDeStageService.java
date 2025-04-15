package com.example.pi_micro.Offredestage.Services;

import com.example.pi_micro.Offredestage.Class.OffreDeStage;
import com.example.pi_micro.Offredestage.Class.Statut;
import com.example.pi_micro.Offredestage.Repositories.OffreDeStageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OffreDeStageService implements IOffreDeStageService {
    private final OffreDeStageRepository offreDeStageRepository;

    // Créer ou mettre à jour une offre de stage
    public OffreDeStage saveOffre(OffreDeStage offreDeStage) {

        return offreDeStageRepository.save(offreDeStage);
    }

    // Mettre à jour le statut de l'offre avec gestion d'erreur
    @Transactional
    public OffreDeStage updateStatut(Long id, Statut statut) {
        OffreDeStage offre = offreDeStageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offre de stage non trouvée avec ID : " + id));
        offre.setStatut(statut);
        return offreDeStageRepository.save(offre);
    }

    // Supprimer une offre de stage avec gestion d'erreur
    @Transactional
    public void deleteOffre(Long id) {
        if (!offreDeStageRepository.existsById(id)) {
            throw new RuntimeException("Impossible de supprimer, offre non trouvée avec ID : " + id);
        }
        offreDeStageRepository.deleteById(id);
    }

    // Lire toutes les offres
    public List<OffreDeStage> getAllOffres() {
        return offreDeStageRepository.findAll();
    }

    // Lire une offre par son ID avec gestion d'erreur
    public OffreDeStage getOffreById(Long id) {
        return offreDeStageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offre de stage non trouvée avec ID : " + id));
    }
    public List<OffreDeStage> searchOffres(String titre, Statut statut) {
        return offreDeStageRepository.search(titre,  statut);
    }
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();

        long total = offreDeStageRepository.count();
        stats.put("total", total);

        for (Statut statut : Statut.values()) {
            long count = offreDeStageRepository.countByStatut(statut);
            stats.put(statut.name(), count);
        }

        return stats;
    }
}
