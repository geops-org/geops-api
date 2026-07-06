package com.geopslabs.geops.api.reviews.domain.model.queries;

/**
 * GetReviewsByOfferIdQuery
 * Query record to retrieve all reviews of an offer.
 * This query supports the offer detail view.
 *
 * @param offerId The offer unique identifier
 * @summary Query to retrieve reviews by offer
 * @since 5.0
 * @author GeOps Labs
 */
public record GetReviewsByOfferIdQuery(Long offerId) {
    public GetReviewsByOfferIdQuery {
        if (offerId == null || offerId < 1)
            throw new IllegalArgumentException("Offer Id cannot be null or less than 1");
    }
}
