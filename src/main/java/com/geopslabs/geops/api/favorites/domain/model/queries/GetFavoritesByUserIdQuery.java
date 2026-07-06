package com.geopslabs.geops.api.favorites.domain.model.queries;

/**
 * GetFavoritesByUserIdQuery
 * Query record to retrieve all favorites saved by a user.
 * This query supports the consumer "My Favorites" view.
 *
 * @param userId The user unique identifier
 * @summary Query to retrieve favorites by user
 * @since 5.0
 * @author GeOps Labs
 */
public record GetFavoritesByUserIdQuery(Long userId) {
    public GetFavoritesByUserIdQuery {
        if (userId == null || userId < 1)
            throw new IllegalArgumentException("User Id cannot be null or less than 1");
    }
}
