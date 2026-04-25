package com.geopslabs.geops.api.reviews.domain.services;

public interface UserValidationPort {
    boolean existsById(Long userId);
}
