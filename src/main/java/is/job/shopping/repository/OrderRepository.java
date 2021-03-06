package is.job.shopping.repository;

import is.job.shopping.model.Order;
import is.job.shopping.model.enums.OrderStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    @Query("SELECT o FROM Order o WHERE o.number=:number")
    Optional<Order> findByOrderNumber(Long number);

    @Query("SELECT o FROM Order o WHERE o.user.email=:userEmail AND o.orderStatus=:complete")
    List<Order> findOrdersForCurrentUser(String userEmail, OrderStatusEnum complete);
}
