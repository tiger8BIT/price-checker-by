package pricecheckerby.repository;

import org.springframework.data.repository.CrudRepository;
import pricecheckerby.model.Email;

import java.util.Optional;

public interface EmailRepository extends CrudRepository<Email, Long> {
    Optional<Email> findEmailByEmail(String email);
    void deleteAllByIsConfirmFalse();
}
