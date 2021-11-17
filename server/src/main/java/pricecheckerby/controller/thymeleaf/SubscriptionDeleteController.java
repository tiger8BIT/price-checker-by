package pricecheckerby.controller.thymeleaf;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pricecheckerby.service.SubscriptionService;

@RequiredArgsConstructor
@Controller
public class SubscriptionDeleteController {
    private final SubscriptionService subscriptionService;
    @CrossOrigin
    @GetMapping("subscription/delete")
    public String deleteSubscription(@RequestParam String token, Model model) {
        subscriptionService.deleteByDeleteToken(token);
        model.addAttribute("message",
                "Подписка успешно удалена");
        return "delete";
    }
}
