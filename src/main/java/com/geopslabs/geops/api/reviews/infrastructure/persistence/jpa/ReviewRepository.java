package com.geopslabs.geops.api.reviews.infrastructure.persistence.jpa;

import com.geopslabs.geops.api.reviews.domain.model.aggregates.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ReviewRepository
 * JPA Repository interface for Review aggregate root.
 * This repository provides data access operations for reviews,
 * including retrieval by offer and duplicate detection.
 *
 * @summary JPA Repository for review data access operations
 * @since 5.0
 * @author GeOps Labs
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**
     * Finds all reviews of an offer
     * @param offerId The offer unique identifier
     * @return A list of Review objects for the given offer
     */
    List<Review> findByOfferId(Long offerId);

    /**
     * Checks whether a review already exists for a user and offer
     * @param userId The user unique identifier
     * @param offerId The offer unique identifier
     * @return true if the review exists
     */
    boolean existsByUserIdAndOfferId(Long userId, Long offerId);
}
