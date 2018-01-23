package fr.denoria.client.space.repositories;

import fr.denoria.client.space.models.Admin;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdminRepository extends CrudRepository<Admin, String> {

    Admin findAdminByPseudo(String pseudo);

    Admin findAdminByMail(String mail);

    Admin findAdminByToken(String token);

    @Override
    List<Admin> findAll();

    List<Admin> findAllByTeam(String team);

    Admin deleteAdminByPseudo(String pseudo);

    Admin deleteAdminByMail(String mail);

    Admin deleteAdminByToken(String token);
}
