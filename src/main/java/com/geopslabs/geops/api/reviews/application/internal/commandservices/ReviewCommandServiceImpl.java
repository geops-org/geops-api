package com.geopslabs.geops.api.reviews.application.internal.commandservices;

import com.geopslabs.geops.api.reviews.domain.model.aggregates.Review;
import com.geopslabs.geops.api.reviews.domain.model.commands.CreateReviewCommand;
import com.geopslabs.geops.api.reviews.domain.services.ReviewCommandService;
import com.geopslabs.geops.api.reviews.infrastructure.persistence.jpa.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * ReviewCommandServiceImpl
 * Implementation of the ReviewCommandService that handles all command operations
 * for reviews. This service enforces the business rule of one review per user
 * per offer, following DDD principles.
 *
 * @summary Implementation of review command service operations
 * @since 5.0
 * @author GeOps Labs
 */
@Service
@Transactional
public class ReviewCommandServiceImpl implements ReviewCommandService {

    private final ReviewRepository reviewRepository;

    public ReviewCommandServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Review> handle(CreateReviewCommand command) {
        try {
            if (reviewRepository.existsByUserIdAndOfferId(command.userId(), command.offerId()))
                throw new IllegalArgumentException(
                        "User " + command.userId() + " already reviewed offer " + command.offerId());

            var review = new Review(command);
            reviewRepository.save(review);
            return Optional.of(review);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
