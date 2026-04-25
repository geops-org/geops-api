package com.geopslabs.geops.api.favorites.domain.services;

public interface OfferExistencePort {
    boolean existsById(Long offerId);
}
