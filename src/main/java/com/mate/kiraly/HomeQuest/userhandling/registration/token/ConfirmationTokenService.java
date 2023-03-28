package com.mate.kiraly.HomeQuest.userhandling.registration.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token){
        confirmationTokenRepository.save(token);
    }

    public ConfirmationToken getToken(String token){
        Optional<ConfirmationToken> fetchedToken = confirmationTokenRepository.findByToken(token);

        if(fetchedToken.isPresent()){
            return fetchedToken.get();
        }
        throw new IllegalStateException("Token not found");
    }

    public void setConfirmedAt(ConfirmationToken token){
        token.setConfirmedAt(LocalDateTime.now());
        confirmationTokenRepository.save(token);
    }
}
