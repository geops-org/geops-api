package com.geopslabs.geops.api.reviews.domain.services;

import java.util.Optional;

public interface OfferQueryPort {
    boolean existsById(Long offerId);
    Optional<String> getTitleById(Long offerId);
}
