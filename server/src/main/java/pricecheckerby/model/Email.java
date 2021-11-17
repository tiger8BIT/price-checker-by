package pricecheckerby.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name="is_confirm")
    private Boolean isConfirm = false;
    @OneToMany(mappedBy = "email", cascade = CascadeType.REMOVE)
    private Collection<Subscription> subscriptions = new ArrayList<>();

    public Email(String email) {
        this.email = email;
    }
}
