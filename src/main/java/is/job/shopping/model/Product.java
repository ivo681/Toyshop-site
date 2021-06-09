package is.job.shopping.model;

import is.job.shopping.model.enums.StatusEnum;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product extends BaseEntity{
    private String name;
    private BigDecimal price;
    private StatusEnum status;
    private Order order;

    public Product() {
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "price", nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    @ManyToOne
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
