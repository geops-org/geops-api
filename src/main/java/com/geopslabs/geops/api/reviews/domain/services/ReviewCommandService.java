package com.geopslabs.geops.api.reviews.domain.services;

import com.geopslabs.geops.api.reviews.domain.model.aggregates.Review;
import com.geopslabs.geops.api.reviews.domain.model.commands.CreateReviewCommand;

import java.util.Optional;

/**
 * ReviewCommandService
 * Domain service interface that defines command operations for reviews.
 *
 * @summary Service interface for handling review command operations
 * @since 5.0
 * @author GeOps Labs
 */
public interface ReviewCommandService {

    /**
     * Handles the creation of a review, enforcing one review per user per offer
     * @param command The command containing the review data
     * @return The created review, or empty if the operation failed
     */
    Optional<Review> handle(CreateReviewCommand command);
}
