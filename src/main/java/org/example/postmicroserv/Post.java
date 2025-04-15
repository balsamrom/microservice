package org.example.postmicroserv;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Utilisation de l'auto-incrémentation pour Long
    private Long id;  // ID de type Long

    private String titre;             // Le titre du post

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // Le contenu du post

    private String imageUrl; // L'URL de l'image (si applicable)
    private String link; // Lien externe (si applicable)

    @Column(nullable = false)
    private LocalDateTime timestamp; // Date et heure de publication

    @Column(nullable = false)
    private boolean isApproved; // Statut de validation (si approuvé par l'admin)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }
}
