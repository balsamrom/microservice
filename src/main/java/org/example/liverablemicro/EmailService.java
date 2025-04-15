package org.example.liverablemicro;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;

import java.io.File;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    // Méthode d'envoi d'un e-mail simple
    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            javaMailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();  // Loggez l'erreur si nécessaire
        }
    }

    // Méthode d'envoi d'un e-mail avec un attachement (si nécessaire)
    public void sendEmailWithAttachment(String to, String subject, String body, String attachmentPath) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);

            // Ajouter un fichier en pièce jointe
            FileSystemResource file = new FileSystemResource(new File(attachmentPath));
            helper.addAttachment("attachment.pdf", file);

            javaMailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();  // Loggez l'erreur si nécessaire
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
