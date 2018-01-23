package fr.denoria.client.space.api;

import fr.denoria.client.space.exceptions.AdminException;
import fr.denoria.client.space.exceptions.RequestException;
import fr.denoria.client.space.models.Admin;
import fr.denoria.client.space.models.dto.AdminDto;
import fr.denoria.client.space.services.AdminService;
import fr.denoria.client.space.services.HttpServletManagerRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping(path = "/api/admin")
public class AdminController {

    private final AdminService adminService;

    private final HttpServletManagerRequestService requestManager;


    @Autowired
    public AdminController(AdminService adminService, HttpServletManagerRequestService requestManager) {
        this.adminService = adminService;
        this.requestManager = requestManager;
    }

    @RequestMapping(method = POST)
    public ResponseEntity<AdminDto> create(@RequestBody Admin admin, HttpServletRequest request)
            throws NoSuchAlgorithmException, AdminException, RequestException {
        if (requestManager.isAuthorizedAdmin(request)) {
            return new ResponseEntity<>(new AdminDto(adminService.create(admin)), OK);
        }
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = POST, path = "/update")
    public ResponseEntity<AdminDto> update(@RequestBody Admin admin, HttpServletRequest request)
            throws NoSuchAlgorithmException, AdminException, RequestException {
        if (requestManager.isAuthorizedAdmin(request)) {
            return new ResponseEntity<>(new AdminDto(adminService.update(admin)), OK);
        }
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = GET)
    public ResponseEntity<List<AdminDto>> findAll(HttpServletRequest request)
            throws AdminException, RequestException {
        if (requestManager.isAuthorizedAdmin(request)) {
            final List<AdminDto> adminDtos = adminService.findAll().stream()
                    .map(AdminDto::new)
                    .collect(Collectors.toList());

            if (adminDtos.isEmpty()) {
                throw new AdminException("La table `Admin` est vide !");
            }

            return new ResponseEntity<>(adminDtos, OK);
        }
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = GET, path = "/count")
    public ResponseEntity<String> coutAll(HttpServletRequest request) throws RequestException, AdminException {
        if (requestManager.isAuthorizedAdmin(request)) {
            return new ResponseEntity<>("{\"count\": " + adminService.countAll() + "}", OK);
        }
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = GET, path = "/pseudo/{pseudo}")
    public ResponseEntity<AdminDto> findByPseudo(@PathVariable(value = "pseudo") final String pseudo, HttpServletRequest request)
            throws AdminException, RequestException {
        if(requestManager.isAuthorizedAdmin(request)) {
            return new ResponseEntity<>(new AdminDto(adminService.findByPseudo(pseudo)), OK);
        }
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = GET, path = "/token/{token}")
    public ResponseEntity<AdminDto> findByToken(@PathVariable(value = "token") final String token, HttpServletRequest request)
            throws AdminException, RequestException {
        if(requestManager.isAuthorizedAdmin(request)){
            return new ResponseEntity<>(new AdminDto(adminService.findByToken(token)), OK);
        }
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = POST, path = "/request-login")
    public ResponseEntity<String> requestLogin(@RequestBody Admin admin) throws AdminException {
        return new ResponseEntity<>("{ \"user-token\": \"" + adminService.requestLogin(admin) + "\" }", OK);
    }
}
