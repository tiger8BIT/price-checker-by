package pricecheckerby.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "domain")
public class Domain {
    @Id
    private String name;
    @Column(name="url", unique=true, nullable=false)
    private String url;
    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "domain")
    private List<Product> products;
    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "domain")
    private List<CatalogUrl> catalogUrls;
}