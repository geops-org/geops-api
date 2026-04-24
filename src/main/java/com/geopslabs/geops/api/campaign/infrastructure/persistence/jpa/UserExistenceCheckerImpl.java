package com.geopslabs.geops.api.campaign.infrastructure.persistence.jpa;

import com.geopslabs.geops.api.campaign.domain.services.UserExistenceChecker;
import com.geopslabs.geops.api.identity.infrastructure.persistence.jpa.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserExistenceCheckerImpl implements UserExistenceChecker {

    private final UserRepository userRepository;

    public UserExistenceCheckerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean existsById(Long userId) {
        return userRepository.existsById(userId);
    }
}
