package com.geopslabs.geops.api.favorites.interfaces.rest.resources;

/**
 * CreateFavoriteResource
 * Resource record for favorite creation requests.
 *
 * @param offerId The offer unique identifier to save as favorite
 * @summary Resource for creating a favorite
 * @since 5.0
 * @author GeOps Labs
 */
public record CreateFavoriteResource(Long offerId) {
}
