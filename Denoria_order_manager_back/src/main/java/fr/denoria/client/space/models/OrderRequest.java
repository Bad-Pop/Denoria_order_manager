package fr.denoria.client.space.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "OrderRequest")
public class OrderRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    @Column(columnDefinition = "varchar(64)")
    private String title;

    @NotNull
    @Column(columnDefinition = "text")
    private String description;

    @NotNull
    private String specificationsLink;

    @NotNull
    private int budget;

    private int price;

    @NotNull
    @Column(columnDefinition = "date", name = "order_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date orderDate;

    /**
     * Ouverte, Validée, En cours, Terminée, Livrée, Refusée
     */
    @NotNull
    @Column(columnDefinition = "varchar(16) default 'Ouverte'")
    private String orderStatus;

    /**
     * EN ATTENTE, PAYEE
     */
    @NotNull
    @Column(columnDefinition = "varchar(16) default 'En attente'")
    private String paymentStatus;

    /**
     * BUILD, DEV
     */
    @NotNull
    @Column(columnDefinition = "varchar(16)")
    private String orderType;

    @NotNull
    @ManyToOne
    private User user;

    public OrderRequest() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", specificationsLink='" + specificationsLink + '\'' +
                ", budget=" + budget +
                ", price=" + price +
                ", orderDate=" + orderDate +
                ", orderStatus='" + orderStatus + '\'' +
                ", user=" + user +
                ", orderType=" + orderType +
                '}';
    }
}
