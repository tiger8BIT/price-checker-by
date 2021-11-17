package pricecheckerby.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pricecheckerby.controller.model.SubscriptionParams;
import pricecheckerby.subscription.mail.EmailMessageService;
import pricecheckerby.model.Email;
import pricecheckerby.model.Price;
import pricecheckerby.model.Product;
import pricecheckerby.model.Subscription;
import pricecheckerby.service.EmailService;
import pricecheckerby.service.PriceService;
import pricecheckerby.service.ProductService;
import pricecheckerby.service.SubscriptionService;

import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class SubscribeController {
    private final EmailService emailService;
    private final SubscriptionService subscriptionService;
    private final ProductService productService;
    private final PriceService priceService;
    private final EmailMessageService emailMessageService;

    @CrossOrigin
    @PostMapping("subscription")
    public @ResponseBody
    ResponseEntity<String> createSubscription(@RequestBody SubscriptionParams params) throws Exception {
        Product product = productService.findByFullUrl(params.getUrl())
                .orElseThrow(() -> new NoSuchElementException("Product is not found"));
        Price price = priceService.getLastPrice(product)
                .orElseThrow(() -> new NoSuchElementException("Last price not found"));
        Optional<Email> optionalEmail = emailService.findEmail(params.getEmail());
        if (optionalEmail.isEmpty()) {
            Email email = emailService.save(new Email(params.getEmail()));
            Subscription subscription = subscriptionService
                    .save(price, params.getReductionAmount(), email);
            emailMessageService.sendConfirmMessage(subscription);
        } else if (!optionalEmail.get().getIsConfirm()) {
            throw new Exception();
        }
        else {
            Email email = optionalEmail.get();
            Subscription subscription = subscriptionService
                    .save(price, params.getReductionAmount(), email);
            emailMessageService.sendDeleteMessage(subscription);
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
