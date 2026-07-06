package com.geopslabs.geops.api.favorites.domain.services;

import com.geopslabs.geops.api.favorites.domain.model.aggregates.Favorite;
import com.geopslabs.geops.api.favorites.domain.model.queries.GetFavoritesByUserIdQuery;

import java.util.List;

/**
 * FavoriteQueryService
 * Domain service interface that defines query operations for favorites.
 * This service handles all GET operations following CQRS design.
 *
 * @summary Service interface for handling favorite query operations
 * @since 5.0
 * @author GeOps Labs
 */
public interface FavoriteQueryService {

    /**
     * Handles the retrieval of all favorites saved by a user
     * @param query The query containing the user unique id
     * @return A list of favorites for the given user
     */
    List<Favorite> handle(GetFavoritesByUserIdQuery query);
}
