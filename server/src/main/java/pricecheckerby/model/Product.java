package pricecheckerby.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "url", nullable = false)
    private String url;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "domain_fk", nullable = false)
    @EqualsAndHashCode.Exclude
    private Domain domain;
    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "product")
    private List<Price> prices;

    public Product(String url, Domain domain) {
        this.url = url;
        this.domain = domain;
    }
}
