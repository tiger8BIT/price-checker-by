package pricecheckerby.repository;

import org.springframework.data.repository.CrudRepository;
import pricecheckerby.model.Price;
import pricecheckerby.model.Subscription;
import java.util.Optional;

public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {
    Optional<Subscription> deleteByDeleteToken(String token);
    Boolean existsByEmailAndStartPrice(String email, Price startPrice);
    Optional<Subscription> findByEmailAndStartPrice(String email, Price startPrice);
}
