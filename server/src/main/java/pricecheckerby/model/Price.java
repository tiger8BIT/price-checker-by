package pricecheckerby.model;

import java.time.LocalDate;
import java.util.Collection;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.experimental.Accessors;

@Entity
@Data
@NoArgsConstructor
@Table(name = "price")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "price")
    private Float price;
    @Column(name = "date", nullable = false)
    private LocalDate date;
    @JsonBackReference
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "product_fk", nullable = false)
    private Product product;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "startPrice")
    private Collection<Subscription> subscriptions;

    public PriceData getData(){
        return new PriceData(this.price, this.date);
    }

    public Price(Float price, LocalDate date, Product product) {
        this.price = price;
        this.date = date;
        this.product = product;
    }
}
