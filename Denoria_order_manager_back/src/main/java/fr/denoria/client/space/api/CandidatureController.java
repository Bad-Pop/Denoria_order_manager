package fr.denoria.client.space.api;

import fr.denoria.client.space.exceptions.AdminException;
import fr.denoria.client.space.exceptions.CandidatureException;
import fr.denoria.client.space.exceptions.RequestException;
import fr.denoria.client.space.exceptions.UserException;
import fr.denoria.client.space.models.Candidature;
import fr.denoria.client.space.models.dto.CandidatureDto;
import fr.denoria.client.space.services.CandidatureService;
import fr.denoria.client.space.services.HttpServletManagerRequestService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping(path = "/api/candidature")
public class CandidatureController {

    private final CandidatureService candidatureService;

    private final HttpServletManagerRequestService requestManager;

    @Autowired
    public CandidatureController(CandidatureService candidatureService, HttpServletManagerRequestService requestManager) {
        this.candidatureService = candidatureService;
        this.requestManager = requestManager;
    }

    @RequestMapping(method = POST)
    public ResponseEntity<CandidatureDto> create(@RequestBody Candidature candidature, HttpServletRequest request)
            throws CandidatureException, UserException, RequestException {
        if(requestManager.isAuthorizedUser(request)) {
            return new ResponseEntity<>(new CandidatureDto(candidatureService.create(candidature)), OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(path = "/user/{pseudo}", method = GET)
    public ResponseEntity<CandidatureDto> findByUser (@PathVariable(value = "pseudo") final String pseudo, HttpServletRequest request)
        throws UserException, RequestException, CandidatureException{

        if(requestManager.isAuthorizedUser(request)) {
            return new ResponseEntity<>(new CandidatureDto(candidatureService.findByUser(pseudo)), OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    //TODO SEE IF CAN BE DELETED
    /*@RequestMapping(method = POST, path = "/update")
    public ResponseEntity<CandidatureDto> update(@RequestBody Candidature candidature) throws CandidatureException, UserException {
        return new ResponseEntity<>(new CandidatureDto(candidatureService.update(candidature)), OK);
    }*/

    @RequestMapping(method = GET)
    public ResponseEntity<List<CandidatureDto>> findAll(HttpServletRequest request) throws CandidatureException, RequestException, AdminException {

        if(requestManager.isAuthorizedAdmin(request)) {
            final List<CandidatureDto> candidatureDtos = candidatureService.findAll().stream()
                    .map(CandidatureDto::new)
                    .collect(Collectors.toList());

            if (candidatureDtos.isEmpty()) {
                throw new CandidatureException("Aucune candidature !");
            }

            return new ResponseEntity<>(candidatureDtos, OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = GET, path = "/id/{id}")
    public ResponseEntity<CandidatureDto> findById(@PathVariable(value = "id") final int id, HttpServletRequest request)
            throws CandidatureException, RequestException, UserException, AdminException {

        if (StringUtils.isEmpty(request.getHeader("z-user")) && StringUtils.isEmpty(request.getHeader("z-admin"))){
            throw new RequestException("Requête non autorisée : connexion requise !");
        } else if (!StringUtils.isEmpty(request.getHeader("z-user"))) {
            if(requestManager.isAuthorizedUser(request)){
                return new ResponseEntity<>(new CandidatureDto(candidatureService.findById(id)), OK);
            }
            throw new RequestException("Requête invalide, vous devez être connecté !");
        } else if (!StringUtils.isEmpty(request.getHeader("z-admin"))) {
            if (requestManager.isAuthorizedAdmin(request)){
                return new ResponseEntity<>(new CandidatureDto(candidatureService.findById(id)), OK);
            }
            throw new RequestException("Requête invalide, vous devez être connecté !");
        }
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }
}
