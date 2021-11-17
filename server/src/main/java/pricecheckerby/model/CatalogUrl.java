package pricecheckerby.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "catalog_url")
public class CatalogUrl {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name="url", unique=true, nullable=false)
    private String url;
    @Nullable
    @Column(name="last_page")
    private Long lastPage;
    @ManyToOne
    @JoinColumn(name = "domain_fk", nullable = false)
    private Domain domain;
    public String getUrl() {
        return domain.getUrl() + url;
    }
}
