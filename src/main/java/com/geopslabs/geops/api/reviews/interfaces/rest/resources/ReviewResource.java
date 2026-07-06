package com.geopslabs.geops.api.reviews.interfaces.rest.resources;

/**
 * ReviewResource
 * Resource record representing a review in REST responses.
 *
 * @param id The review unique identifier
 * @param userId The user unique identifier
 * @param offerId The offer unique identifier
 * @param rating The rating on a 1-5 scale
 * @param comment The review comment
 * @summary Resource representation of a review
 * @since 5.0
 * @author GeOps Labs
 */
public record ReviewResource(Long id, Long userId, Long offerId, Integer rating, String comment) {
}
