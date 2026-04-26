package com.geopslabs.geops.api.reviews.domain.model.exceptions;

public class ReviewAlreadyExistsException extends RuntimeException {
    public ReviewAlreadyExistsException(Long userId, Long offerId) {
        super("Review already exists for userId=" + userId + " and offerId=" + offerId);
    }
}
