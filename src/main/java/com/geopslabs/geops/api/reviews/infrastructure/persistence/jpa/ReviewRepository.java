package com.geopslabs.geops.api.reviews.infrastructure.persistence.jpa;

import com.geopslabs.geops.api.reviews.domain.model.aggregates.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ReviewRepository
 *
 * JPA Repository interface for Review aggregate root
 * This repository provides data access operations for reviews
 *
 * @summary JPA Repository for review data access operations
 * @since 1.0
 * @author GeOps Labs
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**
     * Finds all reviews for a specific offer
     *
     * @param offerId The unique identifier of the offer
     * @return A List of Review objects for the specified offer
     */
    List<Review> findByOfferId(Long offerId);

    List<Review> findByUserId(Long userId);

    List<Review> findByOfferIdOrderByCreatedAtDesc(Long offerId);

    List<Review> findByOfferIdOrderByLikesDesc(Long offerId);

    long countByOfferId(Long offerId);
}
