package pricecheckerby;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pricecheckerby.parser.ParsersExecutorService;
import pricecheckerby.service.EmailService;
import pricecheckerby.service.PriceService;
import pricecheckerby.subscription.SubscriptionNotifierService;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {
    private final ParsersExecutorService parsersExecutorService;
    private final PriceService priceService;
    private final SubscriptionNotifierService subscriptionNotifierService;
    private final EmailService emailService;
    @Scheduled(cron = "${parse.cron}")
    public void tasks() {
        deleteOldPrices();
        loadPrices();
        deleteEmailsNotConfirmed();
        notifySubscription();
    }

    private void deleteOldPrices(){
        priceService.deleteByDateLessThanOneYearOldIfExists();
    }

    private void loadPrices(){
        parsersExecutorService.execute();
    }

    private void notifySubscription() {
        subscriptionNotifierService.notifySubscribers();
    }

    private void deleteEmailsNotConfirmed() {
        emailService.deleteNotConfirmed();
    }
}
