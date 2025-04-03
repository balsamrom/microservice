package org.example.liverablemicro;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivrableService {

    private final LivrableRepository repository;
    private final EmailService emailService;

    public LivrableService(LivrableRepository repository, EmailService emailService) {
        this.repository = repository;
        this.emailService = emailService;
    }

    public List<Livrable> getAll() {
        return repository.findAll();
    }

    public Livrable getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Livrable save(Livrable livrable) {
        Livrable savedLivrable = repository.save(livrable);

        // Envoyer un email lors de l'ajout d'un livrable
        emailService.sendEmail("balsam.romdhane17@gmail.com", "Livrable ajouté", "Le livrable a été ajouté avec succès");

        return savedLivrable;
    }

    public void mail(Livrable livrable) {
        // Envoyer un email lors de l'ajout d'un livrable
        emailService.sendEmail("balsam.romdhane17@gmail.com", "Livrable envoyé", "Le livrable a été envoyé par email");

    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
