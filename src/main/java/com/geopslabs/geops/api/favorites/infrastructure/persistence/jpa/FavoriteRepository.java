package com.geopslabs.geops.api.favorites.infrastructure.persistence.jpa;

import com.geopslabs.geops.api.favorites.domain.model.aggregates.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * FavoriteRepository
 * JPA Repository interface for Favorite aggregate root.
 * This repository provides data access operations for favorites,
 * including retrieval by user and duplicate detection.
 *
 * @summary JPA Repository for favorite data access operations
 * @since 5.0
 * @author GeOps Labs
 */
@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    /**
     * Finds all favorites saved by a user
     * @param userId The user unique identifier
     * @return A list of Favorite objects for the given user
     */
    List<Favorite> findByUserId(Long userId);

    /**
     * Finds a favorite by its user and offer identifiers
     * @param userId The user unique identifier
     * @param offerId The offer unique identifier
     * @return The favorite if it exists
     */
    Optional<Favorite> findByUserIdAndOfferId(Long userId, Long offerId);

    /**
     * Checks whether a favorite already exists for a user and offer
     * @param userId The user unique identifier
     * @param offerId The offer unique identifier
     * @return true if the favorite exists
     */
    boolean existsByUserIdAndOfferId(Long userId, Long offerId);
}
