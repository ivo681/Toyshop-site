package is.job.shopping.service;

import java.math.BigDecimal;

public interface OrderService {
    String createOrder(Long quantity, String productName, String userEmail);

    String getOrderDetails(String orderId);

    BigDecimal getOrderTotal(String orderId);

    void addUnsuccessfulTransaction(String orderId, String number);

    void placeSuccessfulOrder(String orderId, String transactionNumber);
}
