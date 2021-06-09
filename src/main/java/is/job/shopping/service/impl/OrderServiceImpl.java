package is.job.shopping.service.impl;

import is.job.shopping.model.BankTransaction;
import is.job.shopping.model.Order;
import is.job.shopping.model.Product;
import is.job.shopping.model.enums.OrderStatusEnum;
import is.job.shopping.model.enums.StatusEnum;
import is.job.shopping.repository.BankTransactionRepository;
import is.job.shopping.repository.OrderRepository;
import is.job.shopping.repository.ProductRepository;
import is.job.shopping.repository.UserRepository;
import is.job.shopping.service.OrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final BankTransactionRepository bankTransactionRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, BankTransactionRepository bankTransactionRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.bankTransactionRepository = bankTransactionRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String createOrder(Long quantity, String productName, String userEmail) {
        List<Product> availableProductsByName = this.productRepository.getAvailableProductsByName(productName, StatusEnum.AVAILABLE);

        Order order = new Order();
        order.setUser(this.userRepository.findByEmail(userEmail).get());
        order.setOrderStatus(OrderStatusEnum.INCOMPLETE);
        order = this.orderRepository.save(order);
        for (int i = 0; i < quantity; i++) {
            Product currentProduct = availableProductsByName.get(i);
            currentProduct.setOrder(order);
            this.productRepository.save(currentProduct);
        }
        order = this.orderRepository.save(order);
        return order.getId();
    }

    @Override
    public String getOrderDetails(String orderId) {
        Order order = this.orderRepository.findById(orderId).get();
        ArrayList<Product> products = new ArrayList<>(order.getProducts());
        return String.format("%s x %s -> %s", products.size(), products
                        .get(0).getName(),
                (BigDecimal.valueOf(order.getProducts().size()).multiply(order.getProducts().stream().findFirst().get().getPrice())).toPlainString());
    }

    @Override
    public BigDecimal getOrderTotal(String orderId) {
        Order order = this.orderRepository.findById(orderId).get();
        ArrayList<Product> products = new ArrayList<>(order.getProducts());
        return (BigDecimal.valueOf(order.getProducts().size()).multiply(order.getProducts().stream().findFirst().get().getPrice()));
    }

    @Override
    public void addUnsuccessfulTransaction(String orderId, String number) {
        Order order = this.orderRepository.findById(orderId).get();
        order.getBankTransactions().add(this.bankTransactionRepository.findById(number).get());
    }

    @Override
    public void placeSuccessfulOrder(String orderId, String transactionNumber) {
        Order order = this.orderRepository.findById(orderId).get();
        Set<BankTransaction> bankTransactions = order.getBankTransactions();
        bankTransactions.add(this.bankTransactionRepository.findById(transactionNumber).get());
        for (Product product : order.getProducts()) {
            product.setStatus(StatusEnum.SOLD);
            this.productRepository.save(product);
        }
        order.setOrderStatus(OrderStatusEnum.COMPLETE);
        this.orderRepository.save(order);
    }
}
