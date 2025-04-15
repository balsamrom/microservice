package com.example.pi_micro.Offredestage.Repositories;

import com.example.pi_micro.Offredestage.Class.OffreDeStage;
import com.example.pi_micro.Offredestage.Class.Statut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OffreDeStageRepository extends JpaRepository<OffreDeStage, Long> {
    @Query("SELECT o FROM OffreDeStage o " +
            "WHERE (:titre IS NULL OR LOWER(o.titre) LIKE LOWER(CONCAT('%', :titre, '%'))) " +

            "AND (:statut IS NULL OR o.statut = :statut)")
    List<OffreDeStage> search(
            @Param("titre") String titre,

            @Param("statut") Statut statut
    );
    long countByStatut(Statut statut);
}
