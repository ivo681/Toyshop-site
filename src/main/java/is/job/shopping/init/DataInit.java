package is.job.shopping.init;



import is.job.shopping.service.BankAccountService;
import is.job.shopping.service.ProductService;
import is.job.shopping.service.UserRoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements CommandLineRunner {
    private final UserRoleService userRoleService;
    private final BankAccountService bankAccountService;
    private final ProductService productService;

    public DataInit(UserRoleService userRoleService, BankAccountService bankAccountService, ProductService productService) {
        this.userRoleService = userRoleService;
        this.bankAccountService = bankAccountService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.userRoleService.seedRoles();
        this.bankAccountService.seedAccounts();
        this.productService.seedProducts();
    }
}
