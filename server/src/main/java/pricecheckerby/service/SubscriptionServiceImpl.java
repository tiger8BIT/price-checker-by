package pricecheckerby.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pricecheckerby.subscription.mail.JwtUtil;
import pricecheckerby.model.Email;
import pricecheckerby.model.Price;
import pricecheckerby.model.Subscription;
import pricecheckerby.repository.SubscriptionRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    @Value("${jwt.key}")
    private String jwtKey;
    private final SubscriptionRepository subscriptionRepository;
    public Subscription save(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    @Override
    @Transactional
    public Optional<Subscription> deleteByDeleteToken(String token) {
        return subscriptionRepository.deleteByDeleteToken(token);
    }
    @Override
    public List<Subscription> findAll() {
        return (List<Subscription>) subscriptionRepository.findAll();
    }
    public boolean existsByEmailAndStartPrice(String email, Price startPrice) {
        return subscriptionRepository.existsByEmailAndStartPrice(email, startPrice);
    }
    public Optional<Subscription> findByEmailAndStartPrice(String email, Price startPrice) {
        return subscriptionRepository.findByEmailAndStartPrice(email, startPrice);
    }
    @Override
    public Subscription save(Price price, int reductionAmount, Email email){
        Subscription subscription = new Subscription(price, reductionAmount, email);
        subscription = save(subscription);
        String token = JwtUtil.createJWT(email.getEmail(), subscription.getId(), jwtKey, 1);
        subscription.setDeleteToken(token);
        return save(subscription);
    }
    @Override
    public void deleteById(Long id) {
        subscriptionRepository.deleteById(id);
    }
    @Override
    public Optional<Subscription> findById(Long id) {
        return subscriptionRepository.findById(id);
    }
}
