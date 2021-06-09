package is.job.shopping.web;

import is.job.shopping.service.BankTransactionService;
import is.job.shopping.service.OrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
    private final OrderService orderService;
    private final BankTransactionService bankTransactionService;

    public AdminController(OrderService orderService, BankTransactionService bankTransactionService) {
        this.orderService = orderService;
        this.bankTransactionService = bankTransactionService;
    }

    @GetMapping("/menu")
    public String adminMenu(){
        return "admin-menu";
    }

    @GetMapping("/shop/orders")
    public String adminOrderList(Model model){
        model.addAttribute("admin", true);
        model.addAttribute("orders", this.orderService.getAllOrders());
        return "orders-list";
    }

    @GetMapping("/shop/transactions")
    public String adminTransactionList(Model model){
        model.addAttribute("transactions", this.bankTransactionService.getAllTransactionsForOrders());
        return "transactions-list";
    }
}
