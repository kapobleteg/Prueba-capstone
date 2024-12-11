package org.centro.maibo.centromaibo.config;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.centro.maibo.centromaibo.domain.Reservation;
import org.centro.maibo.centromaibo.domain.User;
import org.centro.maibo.centromaibo.dto.ContactDTO;
import org.centro.maibo.centromaibo.properties.MailProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
@AllArgsConstructor
public class MailService {

    private static final String BASE_URL = "baseUrl";
    private static final String USER = "user";
    private final Logger log = LoggerFactory.getLogger(MailService.class);
    private final MailProperties mailProperties;
    private final SpringTemplateEngine templateEngine;
    private final MessageSource messageSource;
    private final JavaMailSender javaMailSender;

    @Async
    public void sendMailFromTemplate(User user, String templateName, String subjectKey) {

        Context context = new Context(Locale.getDefault());
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, mailProperties.getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(subjectKey, null, Locale.forLanguageTag("es-CL"));
        sendMail(user.getEmail(), subject, content);
    }

    @Async
    public void sendUpdateReservation(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("La reserva no puede ser nula");
        }

        Context context = new Context(Locale.getDefault());
        context.setVariable("user", reservation.getUser());
        context.setVariable("reservation", reservation);
        context.setVariable("baseUrl", mailProperties.getBaseUrl());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        context.setVariable("formattedStartDate", dateFormat.format(reservation.getStartDate()));
        context.setVariable("formattedEndDate", dateFormat.format(reservation.getEndDate()));

        String content = templateEngine.process("mail/reservationUpdated", context);
        String subject = messageSource.getMessage("mail.subject.reservationUpdated", null, Locale.forLanguageTag("es-CL"));

        sendMail(reservation.getUser().getEmail(), subject, content);
    }

    @Async
    public void sendContactEmail(ContactDTO contactDTO) {
        if (contactDTO == null) {
            throw new IllegalArgumentException("El formulario de contacto no puede ser nulo");
        }
        Context context = new Context(Locale.getDefault());
        context.setVariable("name", contactDTO.getName());
        context.setVariable("email", contactDTO.getEmail());
        context.setVariable("message", contactDTO.getMessage());
        context.setVariable("baseUrl", mailProperties.getBaseUrl());

        String content = templateEngine.process("mail/contactEmail", context);
        String subject = "Nuevo mensaje de contacto";

        sendMail(mailProperties.getFrom(), subject, content);
    }

    @Async
    public void sendMail(String to, String subject, String content) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            message.setTo(to);
            message.setFrom(mailProperties.getFrom());
            message.setSubject(subject);
            message.setText(content, true);
            javaMailSender.send(mimeMessage);
            log.info("Sent message to User: '{}' ", to);
        } catch (MessagingException e) {
            log.warn("Failed to send email to '{}'", to, e);
        }
    }

    @Async
    public void sendActivationEmail(User user) {
        sendMailFromTemplate(user, "mail/activationEmail", "mail.subject.activation");
    }

    @Async
    public void sendForgottenPasswordEmail(User user) {
        sendMailFromTemplate(user, "mail/forgottenPassword", "mail.subject.forgotPassword");
    }

    @Async
    public void sendReservationAcceptedEmail(User user) {
        sendMailFromTemplate(user, "mail/reservationAccepted", "mail.subject.reservationAccepted");
    }

    @Async
    public void sendReservationRejectedEmail(User user) {
        sendMailFromTemplate(user, "mail/reservationRejected", "mail.subject.reservationRejected");
    }

    @Async
    public void sendScheduledAppointmentEmail(User user) {
        sendMailFromTemplate(user, "mail/scheduledAppointment", "mail.subject.scheduledAppointment");
    }
}