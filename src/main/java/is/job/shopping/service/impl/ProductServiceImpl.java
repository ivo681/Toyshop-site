package is.job.shopping.service.impl;

import com.google.gson.Gson;
import is.job.shopping.model.Product;
import is.job.shopping.model.dtos.ProductSeedDto;
import is.job.shopping.model.enums.StatusEnum;
import is.job.shopping.repository.ProductRepository;
import is.job.shopping.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;

@Service
public class ProductServiceImpl implements ProductService {
    private final static String PRODUCTS_PATH = "src/main/resources/static/json/products.json";
    private final ProductRepository productRepository;
    private final Random random;
    private final Gson gson;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, Random random, Gson gson, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.random = random;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    public void seedProducts() throws IOException {
        if (this.productRepository.count() == 0){
            String content = String.join("", Files.readAllLines(Path.of(PRODUCTS_PATH)));
            ProductSeedDto[] productSeedDtos = this.gson.fromJson(content, ProductSeedDto[].class);
            for (ProductSeedDto productSeedDto : productSeedDtos) {
                Product product = this.modelMapper.map(productSeedDto, Product.class);
                product.setStatus(StatusEnum.AVAILABLE);
                this.productRepository.save(product);
            }

        }
    }

    @Override
    public int getProductCount(String name, StatusEnum status) {
        return this.productRepository.getCountOfProduct(name, StatusEnum.AVAILABLE);
    }

    @Override
    public BigDecimal getProductPrice(String productName) {
        List<Product> productList = this.productRepository.findProductPriceByName(productName);
        return productList.size() > 0 ? productList.get(0).getPrice() : BigDecimal.valueOf(0);
    }
}
