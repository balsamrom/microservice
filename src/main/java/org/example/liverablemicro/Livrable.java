package org.example.liverablemicro;

import jakarta.persistence.*;

@Entity
public class Livrable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String filePath;

    @Enumerated(EnumType.STRING)
    private TypeLivrable type;

    @Enumerated(EnumType.STRING)
    private StatutLivrable statut;

    @ManyToOne
    @JoinColumn(name = "stage_id")
    private StagePFE stage;

    public StagePFE getStage() {
        return stage;
    }

    public void setStage(StagePFE stage) {
        this.stage = stage;
    }

    public StatutLivrable getStatut() {
        return statut;
    }

    public void setStatut(StatutLivrable statut) {
        this.statut = statut;
    }

    public TypeLivrable getType() {
        return type;
    }

    public void setType(TypeLivrable type) {
        this.type = type;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getters, setters
}
