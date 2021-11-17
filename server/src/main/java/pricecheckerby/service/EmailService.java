package pricecheckerby.service;

import pricecheckerby.model.Email;

import java.util.Optional;

public interface EmailService {
    Optional<Email> findEmail(String email);
    Email save(Email email);

    void deleteNotConfirmed();
}
