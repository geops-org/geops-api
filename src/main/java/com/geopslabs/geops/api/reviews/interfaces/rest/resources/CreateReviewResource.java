package com.geopslabs.geops.api.reviews.interfaces.rest.resources;

/**
 * CreateReviewResource
 * Resource record for review creation requests.
 *
 * @param userId The user unique identifier
 * @param rating The rating on a 1-5 scale
 * @param comment Optional comment (max 500 characters)
 * @summary Resource for creating a review
 * @since 5.0
 * @author GeOps Labs
 */
public record CreateReviewResource(Long userId, Integer rating, String comment) {
}
