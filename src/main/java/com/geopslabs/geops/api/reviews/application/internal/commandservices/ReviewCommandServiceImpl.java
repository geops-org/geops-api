package com.geopslabs.geops.api.reviews.application.internal.commandservices;
import com.geopslabs.geops.api.notifications.application.internal.outboundservices.NotificationFactoryService;
import com.geopslabs.geops.api.reviews.domain.model.aggregates.Review;
import com.geopslabs.geops.api.reviews.domain.model.commands.CreateReviewCommand;
import com.geopslabs.geops.api.reviews.domain.model.commands.UpdateReviewCommand;
import com.geopslabs.geops.api.reviews.domain.services.OfferQueryPort;
import com.geopslabs.geops.api.reviews.domain.services.ReviewCommandService;
import com.geopslabs.geops.api.reviews.domain.services.UserValidationPort;
import com.geopslabs.geops.api.reviews.infrastructure.persistence.jpa.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * ReviewCommandServiceImpl
 *
 * Implementation of the ReviewCommandService that handles all command operations
 * for reviews. This service implements the business logic for
 * creating, updating, and managing reviews following DDD principles
 *
 * @author GeOps Labs
 * @summary Implementation of review command service operations
 * @since 1.0
 */
@Service
@Transactional
public class ReviewCommandServiceImpl implements ReviewCommandService {

    private final ReviewRepository reviewRepository;
    private final UserValidationPort userValidationPort;
    private final OfferQueryPort offerQueryPort;
    private final NotificationFactoryService notificationFactory;

    public ReviewCommandServiceImpl(
        ReviewRepository reviewRepository,
        UserValidationPort userValidationPort,
        OfferQueryPort offerQueryPort,
        NotificationFactoryService notificationFactory
    ) {
        this.reviewRepository = reviewRepository;
        this.userValidationPort = userValidationPort;
        this.offerQueryPort = offerQueryPort;
        this.notificationFactory = notificationFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Review> handle(CreateReviewCommand command) {
        try {
            if (!userValidationPort.existsById(command.userId()))
                throw new IllegalArgumentException("User not found with id: " + command.userId());

            if (!offerQueryPort.existsById(command.offerId()))
                throw new IllegalArgumentException("Offer not found with id: " + command.offerId());

            var review = new Review(command);
            var savedReview = reviewRepository.save(review);

            var offerTitle = offerQueryPort.getTitleById(command.offerId()).orElse("Oferta");
            notificationFactory.createReviewCommentNotification(
                command.userId(),
                command.offerId(),
                offerTitle,
                "Tú"
            );

            return Optional.of(savedReview);

        } catch (Exception e) {
            // Log the error with full stacktrace
            System.err.println("Error creating review: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Review> handle(UpdateReviewCommand command) {
        try {
            // Find the existing review by ID
            var existingReviewOpt = reviewRepository.findById(command.id());

            if (existingReviewOpt.isEmpty()) {
                return Optional.empty();
            }

            var existingReview = existingReviewOpt.get();

            // Update the review with new data
            existingReview.updateReview(command);

            // Save the updated review (this should trigger @PreUpdate)
            var updatedReview = reviewRepository.save(existingReview);

            return Optional.of(updatedReview);

        } catch (Exception e) {
            // Log the error with full stacktrace
            System.err.println("Error updating review: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean handleDelete(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("id cannot be null or negative");
        }

        try {
            // First check if review exists
            if (!reviewRepository.existsById(id)) {
                return false;
            }

            // Delete the review
            reviewRepository.deleteById(id);
            return true;

        } catch (Exception e) {
            // Log the error with full stacktrace
            System.err.println("Error deleting review: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
