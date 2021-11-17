package pricecheckerby.subscription;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pricecheckerby.model.Price;
import pricecheckerby.service.PriceService;
import pricecheckerby.service.SubscriptionService;
import pricecheckerby.subscription.mail.EmailMessageService;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SubscriptionNotifierService {
    private final PriceService priceService;
    private final SubscriptionService subscriptionService;
    private final EmailMessageService emailMessageService;
    public void notifySubscribers() {
        subscriptionService.findAll().forEach(subscription -> {
            Price startPrice = subscription.getStartPrice();
            Price currentPrice =  priceService.findBy(startPrice.getProduct(), LocalDate.now()).get(0);
            if (startPrice.getPrice() - currentPrice.getPrice()
                    >= subscription.getReductionAmount()) {
                emailMessageService.sendConfirmMessage(subscription);
                subscriptionService.deleteById(subscription.getId());
            }
        });
    }
}
