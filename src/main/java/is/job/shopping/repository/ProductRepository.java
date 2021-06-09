package is.job.shopping.repository;

import is.job.shopping.model.Product;
import is.job.shopping.model.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    @Query("SELECT COUNT(p) FROM Product p WHERE p.name = :name AND p.status=:status")
    int getCountOfProduct(String name, StatusEnum status);

    @Query("SELECT p FROM Product p WHERE p.name = :name AND p.status=:status")
    List<Product> getAvailableProductsByName(String name, StatusEnum status);

    @Query("SELECT p FROM Product p WHERE p.name = :productName")
    List<Product> findProductPriceByName(String productName);
}
