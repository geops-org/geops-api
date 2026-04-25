package com.geopslabs.geops.api.favorites.domain.services;

public interface UserValidationPort {
    boolean existsById(Long userId);
}
