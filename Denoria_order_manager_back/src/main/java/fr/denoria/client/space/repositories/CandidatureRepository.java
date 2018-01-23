package fr.denoria.client.space.repositories;

import fr.denoria.client.space.models.Candidature;
import fr.denoria.client.space.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CandidatureRepository extends CrudRepository<Candidature, Integer> {

    Candidature findCandidatureById(int id);

    Candidature findCandidatureByUser(User user);

    @Override
    List<Candidature> findAll();

    Candidature deleteCandidatureById(int id);

    Candidature deleteCandidatureByUser(User user);
}
