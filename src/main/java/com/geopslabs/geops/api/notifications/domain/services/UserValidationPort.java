package com.geopslabs.geops.api.notifications.domain.services;

public interface UserValidationPort {
    boolean existsById(Long userId);
}
