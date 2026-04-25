package com.geopslabs.geops.api.favorites.infrastructure.persistence.jpa;

import com.geopslabs.geops.api.favorites.domain.model.aggregates.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * FavoriteRepository
 *
 * JPA Repository interface for Favorite aggregate root
 * This repository provides data access operations for favorites
 *
 * @summary JPA Repository for favorite data access operations
 * @since 1.0
 * @author GeOps Labs
 */
@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    /**
     * Finds all favorites for a specific user
     *
     * @param userId The unique identifier of the user
     * @return A List of Favorite objects for the specified user
     */
    List<Favorite> findByUserId(Long userId);

    Optional<Favorite> findByUserIdAndOfferId(Long userId, Long offerId);

    boolean existsByUserIdAndOfferId(Long userId, Long offerId);

    long countByOfferId(Long offerId);

    long deleteByOfferId(Long offerId);

    long deleteByUserIdAndOfferId(Long userId, Long offerId);
}

