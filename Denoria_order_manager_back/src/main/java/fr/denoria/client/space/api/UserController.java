package fr.denoria.client.space.api;

import fr.denoria.client.space.exceptions.AdminException;
import fr.denoria.client.space.exceptions.RequestException;
import fr.denoria.client.space.exceptions.UserException;
import fr.denoria.client.space.models.User;
import fr.denoria.client.space.models.dto.UserDto;
import fr.denoria.client.space.services.HttpServletManagerRequestService;
import fr.denoria.client.space.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping(path = "/api/user")
public class UserController {

    private final UserService userService;

    private final HttpServletManagerRequestService requestManager;

    @Autowired
    public UserController(UserService userService, HttpServletManagerRequestService requestManager) {
        this.userService = userService;
        this.requestManager = requestManager;
    }

    @RequestMapping(method = POST)
    public ResponseEntity<UserDto> create(@RequestBody User user) throws NoSuchAlgorithmException, UserException {
        return new ResponseEntity<>(new UserDto(userService.create(user)), HttpStatus.OK);
    }

    @RequestMapping(method = POST, path = "/update")
    public ResponseEntity<UserDto> update(@RequestBody User user, HttpServletRequest request)
            throws NoSuchAlgorithmException, UserException, RequestException {
        if (requestManager.isAuthorizedUser(request)) {
            return new ResponseEntity<>(new UserDto(userService.update(user)), HttpStatus.OK);
        }
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = GET)
    public ResponseEntity<List<UserDto>> findAll(HttpServletRequest request) throws UserException, RequestException, AdminException {
        if (requestManager.isAuthorizedAdmin(request)) {
            final List<UserDto> userDtos = userService.findAll().stream()
                    .map(UserDto::new)
                    .collect(Collectors.toList());

            if (userDtos.isEmpty()) {
                throw new UserException("Aucun utilisateur en base de données !");
            }
            return new ResponseEntity<>(userDtos, HttpStatus.OK);
        }
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = GET, path = "/count")
    public ResponseEntity<String> coutAll(HttpServletRequest request) throws RequestException, AdminException {
        if (requestManager.isAuthorizedAdmin(request)) {
            return new ResponseEntity<>("{\"count\": " + userService.countAll() + "}", HttpStatus.OK);
        }
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = GET, path = "/pseudo/{pseudo}")
    public ResponseEntity<UserDto> findByPseudo(@PathVariable(value = "pseudo") final String pseudo, HttpServletRequest request)
            throws UserException, RequestException, AdminException {
        if (requestManager.isAuthorizedAdmin(request)) {
            return new ResponseEntity<>(new UserDto(userService.findByPseudo(pseudo)), HttpStatus.OK);
        }
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }

    //TODO SEE IF CAN BE DELETED
    /*@RequestMapping(method = GET, path = "/token/{token}")
    public ResponseEntity<UserDto> findByToken(@PathVariable(value = "token") final String token) throws UserException {
        return new ResponseEntity<>(new UserDto(userService.findByToken(token)), HttpStatus.OK);
    }*/

    @RequestMapping(method = DELETE, path = "/pseudo/{pseudo}")
    public ResponseEntity<UserDto> deleteByPseudo(@PathVariable(value = "pseudo") final String pseudo, HttpServletRequest request)
            throws UserException, RequestException, AdminException {
        if (requestManager.isAuthorizedAdmin(request)) {
            return new ResponseEntity<>(new UserDto(userService.deleteByPseudo(pseudo)), HttpStatus.OK);
        }
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = DELETE, path = "/mail/{mail}")
    public ResponseEntity<UserDto> deleteByMail(@PathVariable(value = "mail") final String mail, HttpServletRequest request)
            throws UserException, RequestException, AdminException {
        if (requestManager.isAuthorizedAdmin(request)) {
            return new ResponseEntity<>(new UserDto(userService.deleteByMail(mail)), HttpStatus.OK);
        }
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = DELETE, path = "/token/{token}")
    public ResponseEntity<UserDto> deleteByToken(@PathVariable(value = "token") final String token, HttpServletRequest request)
            throws UserException, RequestException, AdminException {
        if (requestManager.isAuthorizedAdmin(request)) {
            return new ResponseEntity<>(new UserDto(userService.deleteByToken(token)), HttpStatus.OK);
        }
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = POST, path = "/request-login")
    public ResponseEntity<UserDto> requestLogin(@RequestBody User user) throws UserException {
        return new ResponseEntity<>(new UserDto(userService.requestLogin(user)), HttpStatus.OK);
    }
}
