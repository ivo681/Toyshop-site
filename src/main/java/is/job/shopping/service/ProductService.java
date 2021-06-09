package is.job.shopping.service;

import is.job.shopping.model.enums.StatusEnum;

import java.io.IOException;
import java.math.BigDecimal;

public interface ProductService {
    void seedProducts() throws IOException;

    int getProductCount(String name, StatusEnum status);

    BigDecimal getProductPrice(String productName);
}
