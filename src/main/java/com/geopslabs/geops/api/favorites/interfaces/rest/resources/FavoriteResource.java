package com.geopslabs.geops.api.favorites.interfaces.rest.resources;

/**
 * FavoriteResource
 * Resource record representing a favorite in REST responses.
 *
 * @param id The favorite unique identifier
 * @param userId The user unique identifier
 * @param offerId The offer unique identifier
 * @summary Resource representation of a favorite
 * @since 5.0
 * @author GeOps Labs
 */
public record FavoriteResource(Long id, Long userId, Long offerId) {
}
