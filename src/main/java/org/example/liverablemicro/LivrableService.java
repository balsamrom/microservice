package org.example.liverablemicro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class LivrableService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private LivrableRepository repository;

    @Autowired
    private EmailService emailService; // Injection du service mail

    // Méthode pour enregistrer un livrable
    public Livrable saveLivrable(TypeLivrable type, MultipartFile file) throws IOException {
        // Générer un nom de fichier unique
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadDir + fileName);

        // Créer le dossier si nécessaire et sauvegarder le fichier
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());

        // Créer et enregistrer le livrable dans la base de données
        Livrable livrable = new Livrable();
        livrable.setType(type);
        livrable.setFileName(file.getOriginalFilename());
        livrable.setFilePath(path.toString());
        livrable.setStatut(StatutLivrable.EN_ATTENTE);
        Livrable savedLivrable = repository.save(livrable);

        // Envoi du mail après l'enregistrement du livrable
        emailService.sendEmail(
                "balsam.romdhane17@gmail.com",
                "✅ Nouveau livrable ajouté",
                "Un livrable de type " + type + " a été ajouté avec succès.\n" +
                        "Nom du fichier : " + file.getOriginalFilename()
        );

        return savedLivrable;
    }

    // Récupérer tous les livrables
    public List<Livrable> getAll() {
        return repository.findAll();
    }

    // Supprimer un livrable
    public void delete(Long id) throws IOException {
        Livrable l = repository.findById(id).orElseThrow(() -> new RuntimeException("Livrable non trouvé"));
        Files.deleteIfExists(Paths.get(l.getFilePath())); // Supprimer le fichier physique
        repository.deleteById(id); // Supprimer le livrable de la base de données
    }

    // Fonction mail indépendante si besoin
    public void mail(Livrable livrable) {
        emailService.sendEmail(
                "balsam.romdhane17@gmail.com",
                "📬 Livrable envoyé",
                "Le livrable de type " + livrable.getType() + " a été envoyé par email."
        );
    }

    // Méthode pour mettre à jour le statut d'un livrable
    @Autowired
    private LivrableRepository livrableRepository;

    // Update the statut of a livrable
    public Livrable updateStatut(Long id, StatutLivrable statut) {
        Livrable livrable = repository.findById(id).orElse(null);
        if (livrable != null) {
            livrable.setStatut(statut);
            return repository.save(livrable);
        }
        return null;
    }}
