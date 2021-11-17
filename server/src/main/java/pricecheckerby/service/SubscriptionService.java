package pricecheckerby.service;

import pricecheckerby.model.Email;
import pricecheckerby.model.Price;
import pricecheckerby.model.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionService {
    Subscription save(Subscription subscription);
    Optional<Subscription> deleteByDeleteToken(String token);

    List<Subscription> findAll();

    Subscription save(Price price, int reductionAmount, Email email);

    void deleteById(Long id);

    Optional<Subscription> findById(Long id);
}
