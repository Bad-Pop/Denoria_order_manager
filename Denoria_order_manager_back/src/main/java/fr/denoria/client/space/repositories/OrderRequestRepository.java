package fr.denoria.client.space.repositories;

import fr.denoria.client.space.models.OrderRequest;
import fr.denoria.client.space.models.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface OrderRequestRepository extends CrudRepository<OrderRequest, Long> {

    OrderRequest findOrderRequestById(int id);

    List<OrderRequest> findOrderRequestByTitle(String title);

    List<OrderRequest> findOrderRequestByBudget(int budget);

    List<OrderRequest> findOrderRequestByPriceBetween(int priceStart, int priceEnd);

    List<OrderRequest> findOrderRequestByOrderDate(Date orderDate);

    List<OrderRequest> findOrderRequestByOrderStatus(String orderStatus);

    List<OrderRequest> findOrderRequestByUser(User user);

    List<OrderRequest> findOrderRequestByOrderType(String orderType);

    List<OrderRequest> findAll(Sort sort);

    OrderRequest deleteOrderRequestById(int id);

    List<OrderRequest> deleteOrderRequestByTitle(String title);

    List<OrderRequest> deleteOrderRequestByOrderDate(Date orderDate);

    List<OrderRequest> deleteOrderRequestByUser(User user);
}
