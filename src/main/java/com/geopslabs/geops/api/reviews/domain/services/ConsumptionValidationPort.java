package com.geopslabs.geops.api.reviews.domain.services;

public interface ConsumptionValidationPort {
    boolean existsByUserAndOffer(Long userId, Long offerId);
}
