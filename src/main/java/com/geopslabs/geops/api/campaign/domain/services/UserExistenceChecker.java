package com.geopslabs.geops.api.campaign.domain.services;

public interface UserExistenceChecker {
    boolean existsById(Long userId);
}
