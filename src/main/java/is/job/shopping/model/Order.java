package is.job.shopping.model;

import is.job.shopping.model.enums.OrderStatusEnum;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity{
    private Set<Product> products = new HashSet<>();
    private User user;
    private OrderStatusEnum orderStatus;
    private Set<BankTransaction> bankTransactions = new HashSet<>();

    public Order() {
    }


    @OneToMany(mappedBy = "order", targetEntity = Product.class,
            fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Set<Product>  getProducts() {
        return products;
    }

    public void setProducts(Set<Product>  products) {
        this.products = products;
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    public OrderStatusEnum getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatusEnum orderStatus) {
        this.orderStatus = orderStatus;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "orders_bank_transactions",
            joinColumns = @JoinColumn(name="order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="bank_transaction_id", referencedColumnName="id"))
    public Set<BankTransaction> getBankTransactions() {
        return bankTransactions;
    }

    public void setBankTransactions(Set<BankTransaction> bankTransactions) {
        this.bankTransactions = bankTransactions;
    }
}
