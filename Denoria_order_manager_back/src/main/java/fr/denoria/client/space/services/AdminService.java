package fr.denoria.client.space.services;

import fr.denoria.client.space.exceptions.AdminException;
import fr.denoria.client.space.models.Admin;
import fr.denoria.client.space.repositories.AdminRepository;
import fr.denoria.client.space.services.utils.RequestLoginUtil;
import fr.denoria.client.space.services.utils.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import static fr.denoria.client.space.consts.ConstVariables.avatarLink;
import static java.lang.Math.toIntExact;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    private final RequestLoginUtil requestLoginUtil;

    private final TokenUtil tokenUtil;

    @Autowired
    public AdminService(AdminRepository adminRepository, RequestLoginUtil requestLoginUtil, TokenUtil tokenUtil) {
        this.adminRepository = adminRepository;
        this.requestLoginUtil = requestLoginUtil;
        this.tokenUtil = tokenUtil;
    }

    public Admin findByPseudo(String pseudo) throws AdminException {

        if (StringUtils.isEmpty(pseudo)) {
            throw new AdminException("Administrateur invalide !");
        }

        Admin admin = adminRepository.findAdminByPseudo(pseudo);

        if (admin == null) {
            throw new AdminException("Aucun administrateur avec ce pseudo !");
        }

        return admin;
    }

    public Admin findByMail(String mail) {
        return adminRepository.findAdminByMail(mail);
    }

    public Admin findByToken(String token) throws AdminException {

        if (StringUtils.isEmpty(token)) {
            throw new AdminException("Veuillez fournir une information valide !");
        }

        return adminRepository.findAdminByToken(token);
    }

    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    public List<Admin> findAllByTeam(String team) {
        return adminRepository.findAllByTeam(team);
    }

    public int countAll() throws ArithmeticException {
        return toIntExact(adminRepository.count());
    }

    public Admin deleteByPseudo(String pseudo) {
        return adminRepository.deleteAdminByPseudo(pseudo);
    }

    public Admin deleteByMail(String mail) {
        return adminRepository.deleteAdminByMail(mail);
    }

    public Admin deleteByToken(String token) {
        return adminRepository.deleteAdminByToken(token);
    }

    public void delete(Admin admin) {
        if (ObjectUtils.isEmpty(adminRepository.findAdminByPseudo(admin.getPseudo()))) {
            adminRepository.delete(admin);
        }
    }

    public Admin create(Admin admin) throws AdminException {

        if (admin == null || StringUtils.isEmpty(admin.getPseudo())
                || StringUtils.isEmpty(admin.getMail())
                || StringUtils.isEmpty(admin.getPassword())
                || StringUtils.isEmpty(admin.getSkype())
                || StringUtils.isEmpty(admin.getTeam())) {
            throw new AdminException("Administrateur invalide !");
        }

        admin.setPassword(requestLoginUtil.passwordHasher(admin.getPassword()));
        admin.setToken(tokenUtil.tokenGenerator());
        admin.setAvatarLink(avatarLink + admin.getPseudo() + "/64.png");

        return adminRepository.save(admin);
    }

    public Admin update(Admin admin) throws AdminException, NoSuchAlgorithmException {
        Admin databaseAdmin = findByPseudo(admin.getPseudo());

        if (databaseAdmin == null) {
            throw new AdminException("Cet administrateur n'éxiste pas !");
        }

        databaseAdmin.setPseudo(admin.getPseudo());
        databaseAdmin.setMail(admin.getMail());
        databaseAdmin.setPassword(requestLoginUtil.passwordHasher(admin.getPassword()));
        databaseAdmin.setAvatarLink(admin.getAvatarLink());
        databaseAdmin.setToken(admin.getToken());
        databaseAdmin.setSkype(admin.getSkype());
        databaseAdmin.setTeam(admin.getTeam());

        return adminRepository.save(databaseAdmin);
    }

    public String requestLogin(Admin admin) throws AdminException {
        if (admin == null || StringUtils.isEmpty(admin.getPseudo())
                || StringUtils.isEmpty(admin.getPassword())) {
            throw new AdminException("L'un des champs fournis est vide ou invalide !");
        }

        Admin databaseAdmin = findByPseudo(admin.getPseudo());

        if (databaseAdmin == null) {
            throw new AdminException("Cet administrateur n'existe pas !");
        } else if (databaseAdmin.getPassword().equalsIgnoreCase(requestLoginUtil.passwordHasher(admin.getPassword()))) {

            databaseAdmin.setToken(tokenUtil.tokenGenerator());
            adminRepository.save(databaseAdmin);
            return databaseAdmin.getToken();
        }

        throw new AdminException("Mauvaise combinaison pseudo mot de passe !");
    }
}
