package fr.denoria.client.space.repositories;

import fr.denoria.client.space.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, String> {

    User findUserByPseudo(String pseudo);

    User findUserByMail(String mail);

    User findUserByToken(String token);

    User deleteUserByPseudo(String pseudo);

    User deleteUserByMail(String mail);

    User deleteUserByToken(String token);

    @Override
    List<User> findAll();
}
