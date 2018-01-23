package fr.denoria.client.space.models.dto;

import fr.denoria.client.space.models.OrderRequest;

import java.util.Date;

public class OrderRequestDto {

    private int id;

    private String title;

    private String description;

    private String specificationsLink;

    private int budget;

    private int price;

    private Date orderDate;

    private String orderStatus;

    private String paymentStatus;

    private String orderType;

    private UserDto user;

    public OrderRequestDto(OrderRequest orderRequest) {
        this.id = orderRequest.getId();
        this.title = orderRequest.getTitle();
        this.description = orderRequest.getDescription();
        this.specificationsLink = orderRequest.getSpecificationsLink();
        this.budget = orderRequest.getBudget();
        this.price = orderRequest.getPrice();
        this.orderDate = orderRequest.getOrderDate();
        this.orderStatus = orderRequest.getOrderStatus();
        this.paymentStatus = orderRequest.getPaymentStatus();
        this.orderType = orderRequest.getOrderType();
        this.user = new UserDto(orderRequest.getUser());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecificationsLink() {
        return specificationsLink;
    }

    public void setSpecificationsLink(String specificationsLink) {
        this.specificationsLink = specificationsLink;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
