package com.geopslabs.geops.api.reviews.domain.model.aggregates;

import com.geopslabs.geops.api.reviews.domain.model.commands.CreateReviewCommand;
import com.geopslabs.geops.api.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;

/**
 * Review Aggregate Root
 *
 * This aggregate represents a consumer review for an offer in the GeOps platform.
 * It manages the rating (1-5 scale) and an optional comment, enforcing the business
 * rule of one review per user per offer.
 *
 * @summary Manages consumer reviews and ratings for offers
 * @since 5.0
 * @author GeOps Labs
 */
@Entity
@Table(name = "reviews", uniqueConstraints = {
        @UniqueConstraint(name = "uk_review_user_offer", columnNames = {"user_id", "offer_id"})
})
@Getter
public class Review extends AuditableAbstractAggregateRoot<Review> {

    /**
     * Identifier of the user that wrote the review
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * Identifier of the reviewed offer
     */
    @Column(name = "offer_id", nullable = false)
    private Long offerId;

    /**
     * Rating of the offer (1-5 scale)
     */
    @Column(name = "rating", nullable = false)
    private Integer rating;

    /**
     * Optional comment describing the consumer experience
     */
    @Column(name = "comment", length = 500)
    private String comment;

    /**
     * Default constructor required by JPA
     */
    protected Review() {
    }

    /**
     * Creates a Review from a CreateReviewCommand
     *
     * @param command The command containing the review data
     */
    public Review(CreateReviewCommand command) {
        this.userId = command.userId();
        this.offerId = command.offerId();
        this.rating = command.rating();
        this.comment = command.comment();
    }
}
