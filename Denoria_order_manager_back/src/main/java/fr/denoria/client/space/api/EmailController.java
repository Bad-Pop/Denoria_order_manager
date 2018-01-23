package fr.denoria.client.space.api;

import fr.denoria.client.space.exceptions.AdminException;
import fr.denoria.client.space.exceptions.NewsletterException;
import fr.denoria.client.space.exceptions.RequestException;
import fr.denoria.client.space.models.Newsletter;
import fr.denoria.client.space.services.EmailService;
import fr.denoria.client.space.services.HttpServletManagerRequestService;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping(path = "/api/newsletter")
public class EmailController {

    private final EmailService emailService;

    private final HttpServletManagerRequestService requestManager;

    @Autowired
    public EmailController(EmailService emailService, HttpServletManagerRequestService requestManager) {
        this.emailService = emailService;
        this.requestManager = requestManager;
    }

    @RequestMapping(method = POST)
    public ResponseEntity<String> send(@RequestBody Newsletter newsletter, HttpServletRequest request)
            throws NewsletterException, MessagingException, TemplateException, IOException, RequestException, AdminException {

        if (requestManager.isAuthorizedAdmin(request)) {
            if (emailService.sendNewslettter(newsletter)) {
                return new ResponseEntity<>("{\"status\" : \"Sending Newsletter !\" }", OK);
            } else {
                throw new NewsletterException("Une erreur est apparue pendant l'envoi de la newsletter !");
            }
        }
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }
}