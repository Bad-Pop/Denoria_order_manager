package fr.denoria.client.space.services;

import fr.denoria.client.space.exceptions.CandidatureException;
import fr.denoria.client.space.exceptions.UserException;
import fr.denoria.client.space.models.Candidature;
import fr.denoria.client.space.models.User;
import fr.denoria.client.space.repositories.CandidatureRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidatureService {

    private final CandidatureRepository candidatureRepository;

    private final UserService userService;

    @Autowired
    public CandidatureService(CandidatureRepository candidatureRepository, UserService userService) {
        this.candidatureRepository = candidatureRepository;
        this.userService = userService;
    }

    public Candidature create(Candidature candidature) throws CandidatureException, UserException {

        if (candidature == null || StringUtils.isEmpty(candidature.getName())
                || candidature.getAge() <= 0
                || StringUtils.isEmpty(candidature.getPersonnalDescription())
                || StringUtils.isEmpty(candidature.getHowLongPlayMinecraft())
                || StringUtils.isEmpty(candidature.getHoursPerWeek())
                || StringUtils.isEmpty(candidature.getWhy())
                || StringUtils.isEmpty(candidature.getDesiredRole())
                || candidature.getUser() == null
                || StringUtils.isEmpty(candidature.getUser().getPseudo())) {
            throw new CandidatureException("Candidature invalide !");
        }

        candidature.setStatus("En attente");
        User user = userService.findByPseudo(candidature.getUser().getPseudo());

        if (user == null) {
            throw new UserException("Cet utilisateur n'existe pas !");
        }

        candidature.setUser(user);

        return candidatureRepository.save(candidature);
    }

    public Candidature update(Candidature candidature) throws UserException, CandidatureException {

        if (candidature == null || StringUtils.isEmpty(candidature.getName())
                || candidature.getAge() <= 0
                || StringUtils.isEmpty(candidature.getPersonnalDescription())
                || StringUtils.isEmpty(candidature.getHowLongPlayMinecraft())
                || StringUtils.isEmpty(candidature.getHoursPerWeek())
                || StringUtils.isEmpty(candidature.getWhy())
                || StringUtils.isEmpty(candidature.getDesiredRole())
                || StringUtils.isEmpty(candidature.getStatus())
                || candidature.getUser() == null
                || StringUtils.isEmpty(candidature.getUser().getPseudo())) {
            throw new CandidatureException("Candidature invalide !");
        }

        Candidature databaseCandidature = findById(candidature.getId());
        databaseCandidature.setStatus(candidature.getStatus());

        return candidatureRepository.save(databaseCandidature);
    }

    public Candidature findById(int id) throws CandidatureException {

        if (id <= 0) {
            throw new CandidatureException("Identifiant invalide !");
        }

        Candidature candidature = candidatureRepository.findCandidatureById(id);

        if (candidature == null) {
            throw new CandidatureException("Aucune candidature ne correspond à votre demande !");
        }

        return candidature;
    }

    public Candidature findByUser(String pseudo) throws UserException, CandidatureException{

        User user = userService.findByPseudo(pseudo);

        if(user == null){
            throw new UserException("Utilisateur invalide");
        }

        Candidature candidature = candidatureRepository.findCandidatureByUser(user);

        if (candidature == null){
            throw new CandidatureException("Vous n'avez pas encore postulé !");
        }

        return candidature;
    }

    public List<Candidature> findAll() {
        return candidatureRepository.findAll();
    }

    public Candidature deleteById(int id) {
        return candidatureRepository.deleteCandidatureById(id);
    }

    public Candidature deleteByUser(User user) {
        return candidatureRepository.deleteCandidatureByUser(user);
    }
}
