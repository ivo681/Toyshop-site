package is.job.shopping.web;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    public HomeController() {
    }

    @GetMapping("/")
    public String index() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return "index";
        }
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }


}
