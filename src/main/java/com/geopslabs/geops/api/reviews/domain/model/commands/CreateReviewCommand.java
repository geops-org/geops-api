package com.geopslabs.geops.api.reviews.domain.model.commands;

/**
 * CreateReviewCommand
 * Command record to create a review for an offer.
 * Enforces that the rating is between 1 and 5.
 *
 * @param userId The user unique identifier
 * @param offerId The offer unique identifier
 * @param rating The rating on a 1-5 scale
 * @param comment Optional comment (max 500 characters)
 * @summary Command to create a review
 * @since 5.0
 * @author GeOps Labs
 */
public record CreateReviewCommand(Long userId, Long offerId, Integer rating, String comment) {
    public CreateReviewCommand {
        if (userId == null || userId < 1)
            throw new IllegalArgumentException("User Id cannot be null or less than 1");
        if (offerId == null || offerId < 1)
            throw new IllegalArgumentException("Offer Id cannot be null or less than 1");
        if (rating == null || rating < 1 || rating > 5)
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        if (comment != null && comment.length() > 500)
            throw new IllegalArgumentException("Comment cannot exceed 500 characters");
    }
}
