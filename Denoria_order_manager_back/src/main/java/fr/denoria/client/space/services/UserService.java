package fr.denoria.client.space.services;

import fr.denoria.client.space.exceptions.UserException;
import fr.denoria.client.space.models.User;
import fr.denoria.client.space.repositories.UserRepository;
import fr.denoria.client.space.services.utils.RequestLoginUtil;
import fr.denoria.client.space.services.utils.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import static fr.denoria.client.space.consts.ConstVariables.avatarLink;
import static java.lang.Math.toIntExact;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final RequestLoginUtil requestLoginUtil;

    private final TokenUtil tokenUtil;

    @Autowired
    public UserService(UserRepository userRepository, RequestLoginUtil requestLoginUtil, TokenUtil tokenUtil) {
        this.userRepository = userRepository;
        this.requestLoginUtil = requestLoginUtil;
        this.tokenUtil = tokenUtil;
    }

    public User findByPseudo(String pseudo) throws UserException {
        if (StringUtils.isEmpty(pseudo)) {
            throw new UserException("Veuillez saisir un pseudo valide !");
        }

        return userRepository.findUserByPseudo(pseudo);
    }

    public User findByToken(String token) throws UserException {

        if (StringUtils.isEmpty(token)) {
            throw new UserException("Veuillez fournir une information valide !");
        }
        return userRepository.findUserByToken(token);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public int countAll() throws ArithmeticException {
        return toIntExact(userRepository.count());
    }

    public User deleteByPseudo(String pseudo) throws UserException {

        if (StringUtils.isEmpty(pseudo)) {
            throw new UserException("Impossible de supprimer ce compte ! Merci de fournir une requête valide.");
        }
        return userRepository.deleteUserByPseudo(pseudo);
    }

    public User deleteByMail(String mail) throws UserException {

        if (StringUtils.isEmpty(mail)) {
            throw new UserException("Veuillez saisir une adresse mail valide !");
        }
        return userRepository.deleteUserByMail(mail);
    }

    public User deleteByToken(String token) throws UserException {

        if (StringUtils.isEmpty(token)) {
            throw new UserException("Merci de saisir une information valide !");
        }

        return userRepository.deleteUserByToken(token);
    }

    public User create(User user) throws UserException, NoSuchAlgorithmException {
        if (user == null || StringUtils.isEmpty(user.getPseudo())
                || StringUtils.isEmpty(user.getMail())
                || StringUtils.isEmpty(user.getPassword())
                || StringUtils.isEmpty(user.getSkype())) {
            throw new UserException("Utilisateur invalide !");
        }

        user.setPassword(requestLoginUtil.passwordHasher(user.getPassword()));
        user.setToken(tokenUtil.tokenGenerator());
        user.setAvatarLink(avatarLink + user.getPseudo() + "/64.png");

        return userRepository.save(user);
    }

    public User update(User user) throws UserException, NoSuchAlgorithmException {

        if(StringUtils.isEmpty(user.getPseudo()) || StringUtils.isEmpty(user.getPassword())
                || StringUtils.isEmpty(user.getMail()) || StringUtils.isEmpty(user.getSkype())){
            throw new UserException("Requête invalide ! Merci de remplir tous les champs !");
        }

        User databaseUser = findByPseudo(user.getPseudo());

        if (databaseUser == null) {
            throw new UserException("Cet utilisateur n'éxiste pas !");
        } else if (!requestLoginUtil.passwordHasher(user.getPassword()).equalsIgnoreCase(databaseUser.getPassword())){
            throw new UserException("Mot de passe incorrecte !");
        }

        databaseUser.setMail(user.getMail());
        databaseUser.setSkype(user.getSkype());

        return userRepository.save(databaseUser);
    }

    public User requestLogin(User user) throws UserException {
        if (user == null || StringUtils.isEmpty(user.getPseudo())
                || StringUtils.isEmpty(user.getPassword())) {
            throw new UserException("L'un des champs fournis est vide ou invalide !");
        }

        User databaseUser = findByPseudo(user.getPseudo());

        if (databaseUser == null) {
            throw new UserException("Cet utilisateur n'existe pas !");
        } else if (databaseUser.getPassword().equalsIgnoreCase(requestLoginUtil.passwordHasher(user.getPassword()))) {

            databaseUser.setToken(tokenUtil.tokenGenerator());
            userRepository.save(databaseUser);
            return databaseUser;
        }

        throw new UserException("Mauvaise combinaison pseudo mot de passe !");
    }
}
