package fr.denoria.client.space.services;

import fr.denoria.client.space.exceptions.OrderRequestException;
import fr.denoria.client.space.exceptions.UserException;
import fr.denoria.client.space.models.OrderRequest;
import fr.denoria.client.space.models.User;
import fr.denoria.client.space.repositories.OrderRequestRepository;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.lang.Math.toIntExact;

@Service
public class OrderRequestService {

    private final OrderRequestRepository orderRequestRepository;

    private final UserService userService;

    private final EmailService emailService;

    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

    @Autowired
    public OrderRequestService(OrderRequestRepository orderRequestRepository, UserService userService, EmailService emailService) {
        this.orderRequestRepository = orderRequestRepository;
        this.userService = userService;
        this.emailService = emailService;
    }

    public OrderRequest create(OrderRequest orderRequest) throws OrderRequestException, UserException {

        if (orderRequest == null || StringUtils.isEmpty(orderRequest.getTitle())
                || StringUtils.isEmpty(orderRequest.getDescription())
                || StringUtils.isEmpty(orderRequest.getSpecificationsLink())
                || orderRequest.getBudget() <= 0
                || StringUtils.isEmpty(orderRequest.getOrderType())
                || orderRequest.getUser() == null
                || StringUtils.isEmpty(orderRequest.getUser().getPseudo())
                || orderRequest.getTitle().length() > 64
                ||orderRequest.getTitle().equalsIgnoreCase("Veuillez selectionner un type de commande ...")) {
            throw new OrderRequestException("Commande invalide ! Veuillez vérifier les champs puis réessayer");
        }

        User user = userService.findByPseudo(orderRequest.getUser().getPseudo());

        if (user == null){
            throw new UserException("Une erreure est apparue pendant l'execution de votre demande !");
        }

        orderRequest.setUser(user);
        orderRequest.setOrderStatus("Ouverte");
        orderRequest.setPaymentStatus("En attente");

        return orderRequestRepository.save(orderRequest);
    }

    public OrderRequest update(OrderRequest orderRequest) throws OrderRequestException, UserException, IOException, TemplateException {

        if (orderRequest == null || StringUtils.isEmpty(orderRequest.getTitle())
                || StringUtils.isEmpty(orderRequest.getDescription())
                || StringUtils.isEmpty(orderRequest.getSpecificationsLink())
                || orderRequest.getBudget() <= 0
                || StringUtils.isEmpty(orderRequest.getOrderStatus())
                || StringUtils.isEmpty(orderRequest.getPaymentStatus())
                || StringUtils.isEmpty(orderRequest.getOrderType())
                || StringUtils.isEmpty(orderRequest.getUser().getPseudo())
                || orderRequest.getTitle().length() > 64) {
            throw new OrderRequestException("Erreur: votre commande est invalide !");
        }

        OrderRequest databaseOrder = findById(orderRequest.getId());

        if (databaseOrder == null) {
            throw new OrderRequestException("Cette commande n'existe pas !");
        }

        databaseOrder.setTitle(orderRequest.getTitle());
        databaseOrder.setDescription(orderRequest.getDescription());
        databaseOrder.setSpecificationsLink(orderRequest.getSpecificationsLink());
        databaseOrder.setBudget(orderRequest.getBudget());
        databaseOrder.setPrice(orderRequest.getPrice());
        databaseOrder.setOrderStatus(orderRequest.getOrderStatus());
        databaseOrder.setPaymentStatus(orderRequest.getPaymentStatus());
        databaseOrder.setOrderType(orderRequest.getOrderType());

        emailService.sendOrderRequestUpdateNotification(databaseOrder.getUser(), databaseOrder.getTitle());

        return orderRequestRepository.save(orderRequest);
    }

    public OrderRequest findById(int id) throws OrderRequestException {

        if (id <= 0) {
            throw new OrderRequestException("Indentifiant invalide !");
        }

        return orderRequestRepository.findOrderRequestById(id);
    }

    public List<OrderRequest> findByTitle(String title) throws OrderRequestException {

        if (StringUtils.isEmpty(title)) {
            throw new OrderRequestException("Veuillez saisir un titre valide !");
        }

        return orderRequestRepository.findOrderRequestByTitle(title);
    }

    public List<OrderRequest> findByBudget(int budget) {
        return orderRequestRepository.findOrderRequestByBudget(budget);
    }

    public List<OrderRequest> findByPriceBetween(int priceStart, int priceEnd) throws OrderRequestException {

        if (priceStart < 0 || priceEnd < 0) {
            throw new OrderRequestException("Tranche de prix invalide !");
        }
        return orderRequestRepository.findOrderRequestByPriceBetween(priceStart, priceEnd);
    }

    public List<OrderRequest> findByDate(String orderDate) throws OrderRequestException, ParseException {

        if (StringUtils.isEmpty(orderDate)) {
            throw new OrderRequestException("Format de la date invalide !");
        }

        Date date = dateFormatter.parse(orderDate);

        return orderRequestRepository.findOrderRequestByOrderDate(date);
    }

    public List<OrderRequest> findByStatus(String orderStatus) throws OrderRequestException {

        if (StringUtils.isEmpty(orderStatus)) {
            throw new OrderRequestException("Veuillez saisir un status valide !");
        }

        return orderRequestRepository.findOrderRequestByOrderStatus(orderStatus);
    }

    public List<OrderRequest> findByUser(User user) throws OrderRequestException, UserException {

        if (user == null || StringUtils.isEmpty(user.getPseudo())) {
            throw new UserException("Utilisateur invalide !");
        }

        User databaseUser = userService.findByPseudo(user.getPseudo());
        return orderRequestRepository.findOrderRequestByUser(databaseUser);
    }

    public List<OrderRequest> findByOrderType(String orderType) throws OrderRequestException {

        if (StringUtils.isEmpty(orderType)) {
            throw new OrderRequestException("Veuillez saisir un type de commande valide !");
        }
        return orderRequestRepository.findOrderRequestByOrderType(orderType);
    }

    public List<OrderRequest> findAll() {
        return orderRequestRepository.findAll(new Sort(Sort.Direction.DESC, "orderDate"));
    }

    public int countAll() {
        return toIntExact(orderRequestRepository.count());
    }

    public void delete(OrderRequest orderRequest) throws OrderRequestException {
        orderRequestRepository.delete(orderRequest);
    }

    public List<OrderRequest> deleteByTitle(String title) {
        return orderRequestRepository.deleteOrderRequestByTitle(title);
    }

    public List<OrderRequest> deleteByDate(Date date) {
        return orderRequestRepository.deleteOrderRequestByOrderDate(date);
    }

    public List<OrderRequest> deleteByUser(User user) {
        return orderRequestRepository.deleteOrderRequestByUser(user);
    }
}
