package com.geopslabs.geops.api.reviews.application.internal.queryservices;

import com.geopslabs.geops.api.reviews.domain.model.aggregates.Review;
import com.geopslabs.geops.api.reviews.domain.model.queries.GetReviewsByOfferIdQuery;
import com.geopslabs.geops.api.reviews.domain.services.ReviewQueryService;
import com.geopslabs.geops.api.reviews.infrastructure.persistence.jpa.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ReviewQueryServiceImpl
 * Implementation of the ReviewQueryService that handles all query operations
 * for reviews following CQRS design.
 *
 * @summary Implementation of review query service operations
 * @since 5.0
 * @author GeOps Labs
 */
@Service
public class ReviewQueryServiceImpl implements ReviewQueryService {

    private final ReviewRepository reviewRepository;

    public ReviewQueryServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Review> handle(GetReviewsByOfferIdQuery query) {
        return reviewRepository.findByOfferId(query.offerId());
    }
}
