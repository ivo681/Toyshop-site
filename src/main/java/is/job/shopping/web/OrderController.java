package is.job.shopping.web;

import is.job.shopping.model.binding.BankAccountBindingModel;
import is.job.shopping.model.binding.OrderBindingModel;
import is.job.shopping.model.enums.StatusEnum;
import is.job.shopping.service.BankAccountService;
import is.job.shopping.service.OrderService;
import is.job.shopping.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/order")
public class OrderController {
    private final ProductService productService;
    private final OrderService orderService;
    private final BankAccountService bankAccountService;

    public OrderController(ProductService productService, OrderService orderService, BankAccountService bankAccountService) {
        this.productService = productService;
        this.orderService = orderService;
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/ocean")
    public String orderOceanProduct(Model model){
        model.addAttribute("purchaseOcean", true);
        model.addAttribute("ocean", this.productService.getProductCount("Ocean Plushie", StatusEnum.AVAILABLE));
        model.addAttribute("price", this.productService.getProductPrice("Ocean Plushie"));
        if (!model.containsAttribute("orderBindingModel")){
            model.addAttribute("orderBindingModel", new OrderBindingModel());
        }
        model.addAttribute("action", "/order/ocean");
        return "order";
    }

    @GetMapping("/night")
    public String orderNightProduct(Model model){
        model.addAttribute("purchaseNight", true);
        model.addAttribute("night", this.productService.getProductCount("Night Plushie", StatusEnum.AVAILABLE));
        model.addAttribute("price", this.productService.getProductPrice("Night Plushie"));
        if (!model.containsAttribute("orderBindingModel")){
            model.addAttribute("orderBindingModel", new OrderBindingModel());
        }
        model.addAttribute("action", "/order/night");
        return "order";
    }

    @GetMapping("/rose")
    public String orderRoseProduct(Model model){
        model.addAttribute("purchaseRose", true);
        model.addAttribute("rose", this.productService.getProductCount("Rose Plushie", StatusEnum.AVAILABLE));
        model.addAttribute("price", this.productService.getProductPrice("Rose Plushie"));
        if (!model.containsAttribute("orderBindingModel")){
            model.addAttribute("orderBindingModel", new OrderBindingModel());
        }
        model.addAttribute("action", "/order/rose");
        return "order";
    }

    @PostMapping("/ocean")
    public String orderOceanPost(@Valid @ModelAttribute("orderBindingModel") OrderBindingModel orderBindingModel,
                           BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) {
        String userEmail = principal.getName();
        int count = this.productService.getProductCount("Ocean Plushie", StatusEnum.AVAILABLE);
        if (bindingResult.hasErrors() || orderBindingModel.getQuantity() > count) {
            if (orderBindingModel.getQuantity() != null && orderBindingModel.getQuantity() > count){
                bindingResult.rejectValue("quantity", "error.orderBindingModel", "You cannot order more than the available quantity");
            }
            redirectAttributes.addFlashAttribute("orderBindingModel", orderBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.orderBindingModel",
                    bindingResult);
            return "order";
        }
        String orderId = this.orderService.createOrder(orderBindingModel.getQuantity(), "Ocean Plushie", userEmail);
        return "redirect:/order/" + orderId;
    }

    @PostMapping("/night")
    public String orderNightPost(@Valid @ModelAttribute("orderBindingModel") OrderBindingModel orderBindingModel,
                           BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) {
        String userEmail = principal.getName();
        int count = this.productService.getProductCount("Night Plushie", StatusEnum.AVAILABLE);
        if (bindingResult.hasErrors() || orderBindingModel.getQuantity() > count) {
            if (orderBindingModel.getQuantity() != null && orderBindingModel.getQuantity() > count){
                bindingResult.rejectValue("quantity", "error.orderBindingModel", "You cannot order more than the available quantity");
            }
            redirectAttributes.addFlashAttribute("orderBindingModel", orderBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.orderBindingModel",
                    bindingResult);
            return "order";
        }
        String orderId = this.orderService.createOrder(orderBindingModel.getQuantity(), "Night Plushie", userEmail);
        return "redirect:/order/" + orderId;
    }

    @PostMapping("/rose")
    public String orderRosePost(@Valid @ModelAttribute("orderBindingModel") OrderBindingModel orderBindingModel,
                           BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) {
        String userEmail = principal.getName();
        int count = this.productService.getProductCount("Rose Plushie", StatusEnum.AVAILABLE);
        if (bindingResult.hasErrors() || orderBindingModel.getQuantity() > count) {
            if (orderBindingModel.getQuantity() != null && orderBindingModel.getQuantity() > count){
                bindingResult.rejectValue("quantity", "error.orderBindingModel", "You cannot order more than the available quantity");
            }
            redirectAttributes.addFlashAttribute("orderBindingModel", orderBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.orderBindingModel",
                    bindingResult);
            return "order";
        }
        String orderId = this.orderService.createOrder(orderBindingModel.getQuantity(), "Rose Plushie", userEmail);
        return "redirect:/order/" + orderId;
    }

    @GetMapping("/{id}")
    public String orderTransactionPage(@PathVariable("id") String orderId, Model model){
        model.addAttribute("orderDetails", this.orderService.getOrderDetails(orderId));
        if (!model.containsAttribute("bankAccountBindingModel")){
            model.addAttribute("bankAccountBindingModel", new BankAccountBindingModel());
        }
        model.addAttribute("action", "/order/complete-" + orderId);
        return "order-complete";
    }

    @PostMapping("/complete-{id}")
    public String orderTransactionPost(@PathVariable("id") String orderId, @Valid @ModelAttribute("bankAccountBindingModel") BankAccountBindingModel bankAccountBindingModel,
                                BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("bankAccountBindingModel", bankAccountBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.bankAccountBindingModel",
                    bindingResult);
            return "order-complete";
        }

        if (!this.bankAccountService.checkIfBankIsValid(bankAccountBindingModel.getNumber(), bankAccountBindingModel.getCvv(),
                bankAccountBindingModel.getFullName(), bankAccountBindingModel.getValidTo())){
            bindingResult.rejectValue("number", "error.bankAccountBindingModel", "Invalid bank account details");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.bankAccountBindingModel",
                    bindingResult);
            return "order-complete";
        };

        if (!this.bankAccountService.hasEnoughBalance(orderId, bankAccountBindingModel.getNumber())){
            String transactionId = this.bankAccountService.createUnsuccessfulTransaction(orderId, bankAccountBindingModel.getNumber());
            this.orderService.addUnsuccessfulTransaction(orderId, transactionId);
            bindingResult.rejectValue("number", "error.bankAccountBindingModel", "Payment has been declined, your purchase was unsuccessful");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.bankAccountBindingModel",
                    bindingResult);
            return "order-complete";
        }
        String successfulTransaction = this.bankAccountService.createSuccessfulTransaction(orderId, bankAccountBindingModel.getNumber());
        this.orderService.placeSuccessfulOrder(orderId, successfulTransaction);
        return "home";

    }
}
