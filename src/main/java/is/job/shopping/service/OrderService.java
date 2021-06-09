package is.job.shopping.service;

import is.job.shopping.model.view.OrderViewModel;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    String createOrder(Long quantity, String productName, String userEmail);

    String getOrderDetails(String orderId);

    BigDecimal getOrderTotal(String orderId);

    void addUnsuccessfulTransaction(String orderId, String number);

    void placeSuccessfulOrder(String orderId, String transactionNumber);

    boolean isOrderNumberTaken(Long number);

    List<OrderViewModel> getUserOrders(String userEmail);

    List<OrderViewModel> getAllOrders();
}
