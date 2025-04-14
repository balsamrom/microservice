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

    public Livrable saveLivrable(TypeLivrable type, MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadDir + fileName);
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());

        Livrable livrable = new Livrable();
        livrable.setType(type);
        livrable.setFileName(file.getOriginalFilename());
        livrable.setFilePath(path.toString());
        livrable.setStatut(StatutLivrable.EN_ATTENTE);
        Livrable savedLivrable = repository.save(livrable);

        // Envoi du mail après l'enregistrement
        emailService.sendEmail(
                "balsam.romdhane17@gmail.com",
                "✅ Nouveau livrable ajouté",
                "Un livrable de type " + type + " a été ajouté avec succès.\n" +
                        "Nom du fichier : " + file.getOriginalFilename()
        );

        return savedLivrable;
    }

    public List<Livrable> getAll() {
        return repository.findAll();
    }

    public void delete(Long id) throws IOException {
        Livrable l = repository.findById(id).orElseThrow();
        Files.deleteIfExists(Paths.get(l.getFilePath()));
        repository.deleteById(id);
    }

    // Fonction mail indépendante si besoin
    public void mail(Livrable livrable) {
        emailService.sendEmail(
                "balsam.romdhane17@gmail.com",
                "📬 Livrable envoyé",
                "Le livrable de type " + livrable.getType() + " a été envoyé par email."
        );
    }
}
