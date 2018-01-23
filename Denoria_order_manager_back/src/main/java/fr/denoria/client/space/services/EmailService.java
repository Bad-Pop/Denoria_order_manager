package fr.denoria.client.space.services;

import fr.denoria.client.space.exceptions.NewsletterException;
import fr.denoria.client.space.models.Admin;
import fr.denoria.client.space.models.Newsletter;
import fr.denoria.client.space.models.User;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fr.denoria.client.space.consts.ConstVariables.freemarkerBasePackagePath;
import static fr.denoria.client.space.consts.ConstVariables.newsletterSetFromContact;

@Service
public class EmailService {

    private final UserService userService;

    private final JavaMailSender sender;

    private final Configuration freemarkerConfig;

    private final AdminService adminService;

    @Autowired
    public EmailService(UserService userService, JavaMailSender sender, Configuration freemarkerConfig, AdminService adminService) {
        this.userService = userService;
        this.sender = sender;
        this.freemarkerConfig = freemarkerConfig;
        this.adminService = adminService;

        freemarkerConfig.setClassForTemplateLoading(this.getClass(), freemarkerBasePackagePath);
    }

    public boolean sendNewslettter(Newsletter newsletter) throws NewsletterException, MessagingException, IOException, TemplateException {

        if (newsletter == null || StringUtils.isEmpty(newsletter.getSubject())
                || StringUtils.isEmpty(newsletter.getTitle())
                || StringUtils.isEmpty(newsletter.getBody())) {
            throw new NewsletterException("Newsletter invalide !");
        }

        List<User> users = userService.findAll();
        List<Admin> admins = adminService.findAll();

        List<String> emails = new ArrayList<>();

        if (users.isEmpty() && admins.isEmpty()) {
            throw new NewsletterException("Aucun utilisateur pour envoyer la newsletter !");
        } else {
            if (!users.isEmpty()) {
                for (User user : users) {
                    emails.add(user.getMail());
                }
            }
            if (!admins.isEmpty()) {
                for (Admin admin : admins) {
                    emails.add(admin.getMail());
                }
            }
        }

        Map<String, String> model = new HashMap<>();
        model.put("subject", newsletter.getSubject());
        model.put("title", newsletter.getTitle());
        model.put("body", newsletter.getBody());

        String text = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfig.getTemplate("newsletter.ftl"), model);

        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
            message.setSubject(newsletter.getSubject());
            message.setBcc(emails.toArray(new String[0]));
            message.setFrom(newsletterSetFromContact);
            message.setText(text, true);
        };

        sender.send(preparator);

        return true;
    }

    public boolean sendOrderRequestUpdateNotification(User user, String orderTitle) throws IOException, TemplateException {

        Map<String, String> model = new HashMap<>();
        model.put("pseudo", user.getPseudo());
        model.put("orderTitle", orderTitle);

        String text = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfig.getTemplate("orderUpdate.ftl"), model);

        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
            message.setSubject("Votre commande a été modifiée");
            message.setTo(user.getMail());
            message.setFrom(newsletterSetFromContact);
            message.setText(text, true);
        };

        sender.send(preparator);

        return true;
    }
}
