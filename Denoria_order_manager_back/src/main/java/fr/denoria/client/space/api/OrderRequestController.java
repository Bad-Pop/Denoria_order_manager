package fr.denoria.client.space.api;

import fr.denoria.client.space.exceptions.AdminException;
import fr.denoria.client.space.exceptions.OrderRequestException;
import fr.denoria.client.space.exceptions.RequestException;
import fr.denoria.client.space.exceptions.UserException;
import fr.denoria.client.space.models.OrderRequest;
import fr.denoria.client.space.models.User;
import fr.denoria.client.space.models.dto.OrderRequestDto;
import fr.denoria.client.space.services.HttpServletManagerRequestService;
import fr.denoria.client.space.services.OrderRequestService;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping(path = "/api/order")
public class OrderRequestController {

    private OrderRequestService orderRequestService;

    private HttpServletManagerRequestService requestManager;

    @Autowired
    public OrderRequestController(OrderRequestService orderRequestService, HttpServletManagerRequestService requestManager) {
        this.orderRequestService = orderRequestService;
        this.requestManager = requestManager;
    }

    @RequestMapping(method = POST)
    public ResponseEntity<OrderRequestDto> create(@RequestBody OrderRequest orderRequest, HttpServletRequest request)
            throws OrderRequestException, UserException, RequestException {
        if (requestManager.isAuthorizedUser(request)) {
            return new ResponseEntity<>(new OrderRequestDto(orderRequestService.create(orderRequest)), HttpStatus.OK);
        }
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = POST, path = "/update")
    public ResponseEntity<OrderRequestDto> update(@RequestBody OrderRequest orderRequest, HttpServletRequest request)
            throws OrderRequestException, UserException, RequestException, AdminException, IOException, TemplateException {

        if (StringUtils.isEmpty(request.getHeader("z-user")) && StringUtils.isEmpty(request.getHeader("z-admin"))){
            throw new RequestException("Requête non autorisée : connexion requise !");
        } else if (!StringUtils.isEmpty(request.getHeader("z-user"))) {
            if(requestManager.isAuthorizedUser(request)){
                return new ResponseEntity<>(new OrderRequestDto(orderRequestService.update(orderRequest)), HttpStatus.OK);
            }
            throw new RequestException("Requête invalide, vous devez être connecté !");
        } else if (!StringUtils.isEmpty(request.getHeader("z-admin"))) {
            if (requestManager.isAuthorizedAdmin(request)){
                return new ResponseEntity<>(new OrderRequestDto(orderRequestService.update(orderRequest)), HttpStatus.OK);
            }
            throw new RequestException("Requête invalide, vous devez être connecté !");
        }
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = GET)
    public ResponseEntity<List<OrderRequestDto>> findAll(HttpServletRequest request)
            throws OrderRequestException, RequestException, AdminException {
        if(requestManager.isAuthorizedAdmin(request)) {
            final List<OrderRequestDto> orderRequestDtos = orderRequestService.findAll()
                    .stream()
                    .map(OrderRequestDto::new)
                    .collect(Collectors.toList());

            if (orderRequestDtos.isEmpty()) {
                throw new OrderRequestException("Aucune commande !");
            }

            return new ResponseEntity<>(orderRequestDtos, HttpStatus.OK);
        }
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = GET, path = "/count")
    public ResponseEntity<String> coutAll(HttpServletRequest request) throws RequestException, AdminException {
        if(requestManager.isAuthorizedAdmin(request)) {
            return new ResponseEntity<>("{\"count\": " + orderRequestService.countAll() + "}", HttpStatus.OK);
        }
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = GET, path = "/id/{id}")
    public ResponseEntity<OrderRequestDto> findById(@PathVariable(value = "id") final int id, HttpServletRequest request)
            throws OrderRequestException, RequestException, AdminException, UserException {

        if (StringUtils.isEmpty(request.getHeader("z-user")) && StringUtils.isEmpty(request.getHeader("z-admin"))){
            throw new RequestException("Requête non autorisée, connexion requise");
        } else if (!StringUtils.isEmpty(request.getHeader("z-user"))) {
            if(requestManager.isAuthorizedUser(request)){
                return new ResponseEntity<>(new OrderRequestDto(orderRequestService.findById(id)), HttpStatus.OK);
            }
            throw new RequestException("Requête invalide, vous devez être connecté !");
        } else if (!StringUtils.isEmpty(request.getHeader("z-admin"))) {
            if (requestManager.isAuthorizedAdmin(request)){
                return new ResponseEntity<>(new OrderRequestDto(orderRequestService.findById(id)), HttpStatus.OK);
            }
            throw new RequestException("Requête invalide, vous devez être connecté !");
        }
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = GET, path = "/title/{title}")
    public ResponseEntity<List<OrderRequestDto>> findByTitle(@PathVariable(value = "title") final String title, HttpServletRequest request)
            throws OrderRequestException, RequestException, AdminException, UserException {

        final List<OrderRequestDto> orderRequestDtos = orderRequestService.findByTitle(title)
                .stream()
                .map(OrderRequestDto::new)
                .collect(Collectors.toList());

        if (orderRequestDtos.isEmpty()) {
            throw new OrderRequestException("Aucune commande avec ce titre : " + title);
        }

        if (StringUtils.isEmpty(request.getHeader("z-user")) && StringUtils.isEmpty(request.getHeader("z-admin"))){
            throw new RequestException("Requête non autorisée, connexion requise");
        } else if (!StringUtils.isEmpty(request.getHeader("z-user"))) {
            if(requestManager.isAuthorizedUser(request)){
                return new ResponseEntity<>(orderRequestDtos, HttpStatus.OK);
            }
            throw new RequestException("Requête invalide, vous devez être connecté !");
        } else if (!StringUtils.isEmpty(request.getHeader("z-admin"))) {
            if (requestManager.isAuthorizedAdmin(request)){
                return new ResponseEntity<>(orderRequestDtos, HttpStatus.OK);
            }
            throw new RequestException("Requête invalide, vous devez être connecté !");
        }
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = GET, path = "price/{priceStart}/{priceEnd}")
    public ResponseEntity<List<OrderRequestDto>> findByPriceBetween(
            @PathVariable(value = "priceStart") final int priceStart,
            @PathVariable(value = "priceEnd") final int priceEnd,
            HttpServletRequest request)
            throws OrderRequestException, RequestException, AdminException {
        if(requestManager.isAuthorizedAdmin(request)) {
            final List<OrderRequestDto> orderRequestDtos = orderRequestService.findByPriceBetween(priceStart, priceEnd)
                    .stream()
                    .map(OrderRequestDto::new)
                    .collect(Collectors.toList());

            if (orderRequestDtos.isEmpty()) {
                throw new OrderRequestException("Aucune commande dans cette tranche de prix !");
            }

            return new ResponseEntity<>(orderRequestDtos, HttpStatus.OK);
        }
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = GET, path = "/date/{date}")
    public ResponseEntity<List<OrderRequestDto>> findByDate(@PathVariable(value = "date") final String date, HttpServletRequest request)
            throws OrderRequestException, ParseException, RequestException, AdminException {

        if(requestManager.isAuthorizedAdmin(request)) {
            final List<OrderRequestDto> orderRequestDtos = orderRequestService.findByDate(date)
                    .stream()
                    .map(OrderRequestDto::new)
                    .collect(Collectors.toList());

            if (orderRequestDtos.isEmpty()) {
                throw new OrderRequestException("Aucune commande pour cette date !");
            }

            return new ResponseEntity<>(orderRequestDtos, HttpStatus.OK);
        }
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = GET, path = "/status/{orderStatus}")
    public ResponseEntity<List<OrderRequestDto>> findByStatus(@PathVariable(value = "orderStatus") final String orderStatus, HttpServletRequest request)
            throws OrderRequestException, RequestException, AdminException {

        if(requestManager.isAuthorizedAdmin(request)) {
            final List<OrderRequestDto> orderRequestDtos = orderRequestService.findByStatus(orderStatus)
                    .stream()
                    .map(OrderRequestDto::new)
                    .collect(Collectors.toList());

            if (orderRequestDtos.isEmpty()) {
                throw new OrderRequestException("Aucune commande avec ce status");
            }

            return new ResponseEntity<>(orderRequestDtos, HttpStatus.OK);
        }
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = POST, path = "/user")
    public ResponseEntity<List<OrderRequestDto>> findByUser(@RequestBody User user, HttpServletRequest request)
            throws OrderRequestException, UserException, RequestException, AdminException {

        final List<OrderRequestDto> userOrderRequestDtos = orderRequestService.findByUser(user)
                .stream()
                .map(OrderRequestDto::new)
                .collect(Collectors.toList());

        if (userOrderRequestDtos.isEmpty()) {
            throw new OrderRequestException("Vous n'avez passé aucune commande !");
        }

        if (StringUtils.isEmpty(request.getHeader("z-user")) && StringUtils.isEmpty(request.getHeader("z-admin"))){
            throw new RequestException("Requête non autorisée, connexion requise");
        } else if (!StringUtils.isEmpty(request.getHeader("z-user"))) {
            if(requestManager.isAuthorizedUser(request)){
                return new ResponseEntity<>(userOrderRequestDtos, HttpStatus.OK);
            }
            throw new RequestException("Requête invalide, vous devez être connecté !");
        } else if (!StringUtils.isEmpty(request.getHeader("z-admin"))) {
            if (requestManager.isAuthorizedAdmin(request)){
                return new ResponseEntity<>(userOrderRequestDtos, HttpStatus.OK);
            }
            throw new RequestException("Requête invalide, vous devez être connecté !");
        }
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = GET, path = "/order-type/{orderType}")
    public ResponseEntity<List<OrderRequestDto>> findByOrderType(@PathVariable(value = "orderType") final String orderType, HttpServletRequest request)
            throws OrderRequestException, RequestException, AdminException {

        if(requestManager.isAuthorizedAdmin(request)) {
            final List<OrderRequestDto> orderRequestDtos = orderRequestService.findByOrderType(orderType)
                    .stream()
                    .map(OrderRequestDto::new)
                    .collect(Collectors.toList());

            if (orderRequestDtos.isEmpty()) {
                throw new OrderRequestException("Aucune commande pour cette équipe !");
            }

            return new ResponseEntity<>(orderRequestDtos, HttpStatus.OK);
        }
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = DELETE)
    public void delete(@RequestBody OrderRequest orderRequest, HttpServletRequest request)
            throws OrderRequestException, RequestException, AdminException {
        if(requestManager.isAuthorizedAdmin(request)) {
            orderRequestService.delete(orderRequest);
        }
    }
}