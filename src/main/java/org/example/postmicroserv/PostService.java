package org.example.postmicroserv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class PostService implements PostServiceImp {
    @Autowired
    private PostRepository postRepository;

    // Liste des mots interdits
    public static final List<String> BAD_WORDS = Arrays.asList(
            "idiot", "stupide", "abruti", "imbécile", "con", "merde",
            "foutre", "salaud", "enculé", "putain", "bordel", "saleté", "shit"
    );

    // Vérifie si le contenu contient des mots interdits
    public boolean containsBadWords(String content) {
        if (content == null) return false;

        for (String word : BAD_WORDS) {
            if (Pattern.compile("(?i)\\b" + Pattern.quote(word) + "\\b").matcher(content).find()) {
                return true;
            }
        }
        return false;
    }

    // Remplace les mots interdits par ****
    public String filterBadWords(String content) {
        if (content == null) return null;

        String filteredContent = content;
        for (String word : BAD_WORDS) {
            String regex = "(?i)\\b" + Pattern.quote(word) + "\\b";
            filteredContent = filteredContent.replaceAll(regex, "****");
        }
        return filteredContent;
    }

    @Override
    public Post createPost(Post post) {
        if (post == null) {
            throw new IllegalArgumentException("Le post ne peut pas être null");
        }

        // Vérification et filtrage des mots interdits
        if (containsBadWords(post.getContent())) {
            throw new IllegalArgumentException("Votre post contient des mots inappropriés !");
        }

        // Applique le filtrage des mots interdits et sauvegarder
        post.setContent(filterBadWords(post.getContent()));
        post.setTimestamp(LocalDateTime.now()); // Définit le timestamp actuel
        return postRepository.save(post);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public Post updatePost(Long id, Post postDetails) {
        if (postDetails == null) {
            throw new IllegalArgumentException("Les détails du post ne peuvent pas être null");
        }

        return postRepository.findById(id).map(post -> {
            // Vérification des mots interdits
            if (containsBadWords(postDetails.getContent())) {
                throw new IllegalArgumentException("Votre post contient des mots inappropriés !");
            }

            // Mise à jour des champs
            post.setTitre(postDetails.getTitre());
            post.setContent(filterBadWords(postDetails.getContent()));
            post.setImageUrl(postDetails.getImageUrl());
            post.setLink(postDetails.getLink());
            return postRepository.save(post);
        }).orElseThrow(() -> new RuntimeException("Post non trouvé avec l'ID : " + id));
    }

    @Override
    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new RuntimeException("Post non trouvé avec l'ID : " + id);
        }
        postRepository.deleteById(id);
    }
}