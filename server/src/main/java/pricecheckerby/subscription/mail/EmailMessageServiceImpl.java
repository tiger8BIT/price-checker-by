package pricecheckerby.subscription.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pricecheckerby.model.Price;
import pricecheckerby.model.Product;
import pricecheckerby.model.Subscription;

@Service
@RequiredArgsConstructor
public class EmailMessageServiceImpl implements EmailMessageService {
    private final JavaMailSender emailSender;
    @Value("${confirm.url}")
    private String confirmUrl;
    @Value("${delete.url}")
    private String deleteUrl;
    public void sendSimpleMessage(
            String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
    public void sendPriceHasDroppedMessage(Product product, Price oldPrice, Price lowerPrice){

    }
    @Override
    public void sendConfirmMessage(Subscription subscription) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("PriceCheckerBy: Подтверждение почтового ящика");
        message.setTo(subscription.getEmail().getEmail());
        String url =  confirmUrl + subscription.getDeleteToken();
        message.setText("Для подтверждения почтового ящика перейдите по ссылке: " + url + "");
        emailSender.send(message);
    }
    @Override
    public void sendPriceHasDroppedMessage(Subscription subscription) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("PriceCheckerBy: Цена на товар снижена");
        message.setTo(subscription.getEmail().getEmail());
        Product product = subscription.getStartPrice().getProduct();
        String productUrl = product.getDomain().getUrl() + product.getUrl();
        message.setText("Цена на товар " + productUrl + " снижена!");
        emailSender.send(message);
    }
    @Override
    public void sendDeleteMessage(Subscription subscription) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("PriceCheckerBy: Подписка успешно оформлена");
        message.setTo(subscription.getEmail().getEmail());
        String url =  deleteUrl + subscription.getDeleteToken();
        Product product = subscription.getStartPrice().getProduct();
        String productUrl = product.getDomain().getUrl() + product.getUrl();
        message.setText("Подписка на товар " +
                productUrl +
                " успешно оформлена! При снижении цены на "
                + subscription.getReductionAmount() +
                "р. вы получите уведомление. Для удаления подписки перейдите по ссылке: " +
                url);
        emailSender.send(message);
    }
}
