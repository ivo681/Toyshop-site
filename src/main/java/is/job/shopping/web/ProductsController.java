package is.job.shopping.web;

import is.job.shopping.model.enums.StatusEnum;
import is.job.shopping.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductsController {
    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public String productsPage(Model model){
        model.addAttribute("ocean", this.productService.getProductCount("Ocean Plushie", StatusEnum.AVAILABLE));
        model.addAttribute("oceanPrice", this.productService.getProductPrice("Ocean Plushie"));
        model.addAttribute("night", this.productService.getProductCount("Night Plushie", StatusEnum.AVAILABLE));
        model.addAttribute("nightPrice", this.productService.getProductPrice("Night Plushie"));
        model.addAttribute("rose", this.productService.getProductCount("Rose Plushie", StatusEnum.AVAILABLE));
        model.addAttribute("rosePrice", this.productService.getProductPrice("Rose Plushie"));
        return "product-list";
    }
}
