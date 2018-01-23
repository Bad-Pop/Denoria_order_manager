package fr.denoria.client.space.services;

import fr.denoria.client.space.exceptions.AdminException;
import fr.denoria.client.space.exceptions.RequestException;
import fr.denoria.client.space.exceptions.UserException;
import fr.denoria.client.space.models.Admin;
import fr.denoria.client.space.models.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class HttpServletManagerRequestService {

    private final UserService userService;

    private final AdminService adminService;

    @Autowired
    public HttpServletManagerRequestService(UserService userService, AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }

    /**
     * Check if the user is authorized for this request
     * @param request the request received by the rest api
     * @return boolean
     * @throws RequestException on error
     */
    public boolean isAuthorizedUser(HttpServletRequest request) throws RequestException, UserException {

        String pseudo = request.getHeader("z-pseudo");
        String token = request.getHeader("z-token");
        boolean role = Boolean.parseBoolean(request.getHeader("z-user"));

        if (StringUtils.isEmpty(pseudo)
                || StringUtils.isEmpty(token)) {
            throw new RequestException("Requête non autorisée : vous devez être connecté !");
        } else {
            if (isLoggedUser(pseudo, token) && role) {
                return true;
            }
        }

        throw new RequestException("Vous n'ête pas autorisé a executer cette action ou vous n'êtes pas connecté !");
    }

    /**
     * Check if user's token is valid
     * @param pseudo user's pseudo
     * @param token  user's token (session)
     * @return boolean
     * @throws RequestException on error
     */
    private boolean isLoggedUser(String pseudo, String token) throws RequestException, UserException {

        User userByPseudo = userService.findByPseudo(pseudo);
        User userByToken = userService.findByToken(token);

        if (userByPseudo == null || userByToken == null
                || StringUtils.isEmpty(userByPseudo.getPseudo())
                || StringUtils.isEmpty(userByPseudo.getToken())
                || StringUtils.isEmpty(userByToken.getPseudo())
                || StringUtils.isEmpty(userByToken.getToken())){
            throw new RequestException("Vous n'ête pas autorisé a executer cette action ou vous n'êtes pas connecté !");
        } else if (userByPseudo.getPseudo().equalsIgnoreCase(userByToken.getPseudo())
                && userByPseudo.getToken().equalsIgnoreCase(userByToken.getToken())){
            return true;
        }

        throw new RequestException("Une erreure est apparue pendant la vérification de votre identité !");
    }

    /**
     * Check if the admin is authorized for this request
     * @param request the request received by the rest api
     * @return boolean
     * @throws RequestException on error
     * @throws AdminException on error
     */
    public boolean isAuthorizedAdmin(HttpServletRequest request) throws RequestException, AdminException {

        String pseudo = request.getHeader("z-pseudo");
        String token = request.getHeader("z-token");
        boolean role = Boolean.parseBoolean(request.getHeader("z-admin"));

        if(StringUtils.isEmpty(pseudo)
                || StringUtils.isEmpty(token)
                || !role){
            throw new RequestException("Requête non autorisée : vous devez être connecté !");
        } else {
            if(isLoggedAdmin(pseudo, token)){
                return true;
            }
        }

        throw new RequestException("Vous devez être connecté en administrateur pour appeller ce service !");
    }

    /**
     * Check if admin's token is valid
     * @param pseudo admin's pseudo
     * @param token admin's token (session)
     * @return boolean
     * @throws RequestException on error
     * @throws AdminException on error
     */
    private boolean isLoggedAdmin(String pseudo, String token) throws RequestException, AdminException {

        Admin adminByPseudo = adminService.findByPseudo(pseudo);
        Admin adminByToken = adminService.findByToken(token);

        if (adminByPseudo == null || adminByToken == null
                || StringUtils.isEmpty(adminByPseudo.getPseudo())
                || StringUtils.isEmpty(adminByPseudo.getToken())
                || StringUtils.isEmpty(adminByToken.getPseudo())
                || StringUtils.isEmpty(adminByToken.getToken())){
            throw new RequestException("Vous n'ête pas autorisé a executer cette action ou vous n'êtes pas connecté !");
        } else if (adminByPseudo.getPseudo().equalsIgnoreCase(adminByToken.getPseudo())
                && adminByPseudo.getToken().equalsIgnoreCase(adminByToken.getToken())){
            return true;
        }

        throw new RequestException("Une erreure est apparue pendant la vérification de votre identité !");
    }
}
