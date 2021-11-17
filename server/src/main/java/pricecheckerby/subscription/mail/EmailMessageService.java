package pricecheckerby.subscription.mail;

import pricecheckerby.model.Subscription;

public interface EmailMessageService {
    void sendConfirmMessage(Subscription subscription);

    void sendPriceHasDroppedMessage(Subscription subscription);

    void sendDeleteMessage(Subscription subscription);
}
