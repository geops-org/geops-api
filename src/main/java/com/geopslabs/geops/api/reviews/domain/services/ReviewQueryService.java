package com.geopslabs.geops.api.reviews.domain.services;

import com.geopslabs.geops.api.reviews.domain.model.aggregates.Review;
import com.geopslabs.geops.api.reviews.domain.model.queries.GetReviewsByOfferIdQuery;

import java.util.List;

/**
 * ReviewQueryService
 * Domain service interface that defines query operations for reviews.
 *
 * @summary Service interface for handling review query operations
 * @since 5.0
 * @author GeOps Labs
 */
public interface ReviewQueryService {

    /**
     * Handles the retrieval of all reviews for an offer
     * @param query The query containing the offer unique id
     * @return A list of reviews for the given offer
     */
    List<Review> handle(GetReviewsByOfferIdQuery query);
}
