package pricecheckerby.controller.thymeleaf;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pricecheckerby.exception.InvalidTokenException;
import pricecheckerby.subscription.mail.EmailMessageService;
import pricecheckerby.subscription.mail.JwtUtil;
import pricecheckerby.subscription.mail.TokenData;
import pricecheckerby.model.Email;
import pricecheckerby.model.Subscription;
import pricecheckerby.service.EmailService;
import pricecheckerby.service.SubscriptionService;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class ConfirmController {
    private final EmailService emailService;
    private final EmailMessageService emailMessageService;
    private final SubscriptionService subscriptionService;
    @Value("${jwt.key}")
    private String jwtKey;
    @CrossOrigin
    @GetMapping("confirm")
    public String confirm(@RequestParam String token, Model model) {
        TokenData tokenData = JwtUtil.tokenDataFrom(token, jwtKey)
                .orElseThrow(InvalidTokenException::new);
        Email email = emailService.findEmail(tokenData.getEmail())
                .orElseThrow(() -> new NoSuchElementException("Email is not found"));
        Subscription subscription = subscriptionService.findById(tokenData.getSubscriptionId())
                .orElseThrow(() -> new NoSuchElementException("Subscription is not found"));
        email.setIsConfirm(true);
        emailService.save(email);
        model.addAttribute("message",
                "Почтовый ящик подтверждён. " +
                        "Письмо для отмены подписки выслано на " +
                        email.getEmail());
        emailMessageService.sendDeleteMessage(subscription);
        return "confirm";
    }
}
