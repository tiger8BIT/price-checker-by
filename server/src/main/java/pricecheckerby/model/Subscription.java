package pricecheckerby.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "subscription")
@Data
@NoArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "email_fk", nullable = false)
    private Email email;
    @ManyToOne
    @JoinColumn(name = "start_price_fk", nullable = false)
    private Price startPrice;
    @Column(name = "reduction_amount", nullable = false)
    private int reductionAmount;
    @Column(name = "delete_token")
    private String deleteToken;
    public Subscription(Price startPrice, int reductionAmount, Email email) {
        this.startPrice = startPrice;
        this.reductionAmount = reductionAmount;
        this.email = email;
    }
}
