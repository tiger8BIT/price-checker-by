package pricecheckerby.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pricecheckerby.model.Email;
import pricecheckerby.repository.EmailRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final EmailRepository emailRepository;
    public Optional<Email> findEmail(String email) {
        return emailRepository.findEmailByEmail(email);
    }
    public Email save(Email email) {
        return emailRepository.save(email);
    }
    @Override
    @Transactional
    public void deleteNotConfirmed() {
        emailRepository.deleteAllByIsConfirmFalse();
    }
}
