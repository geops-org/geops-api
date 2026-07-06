package com.geopslabs.geops.api.favorites.interfaces.rest.transform;

import com.geopslabs.geops.api.favorites.domain.model.aggregates.Favorite;
import com.geopslabs.geops.api.favorites.interfaces.rest.resources.FavoriteResource;

/**
 * FavoriteResourceFromEntityAssembler
 * Assembler to transform a Favorite entity into a FavoriteResource.
 *
 * @summary Assembler for favorite REST resources
 * @since 5.0
 * @author GeOps Labs
 */
public class FavoriteResourceFromEntityAssembler {

    /**
     * Transforms an entity into a REST resource
     * @param entity The favorite entity
     * @return The favorite resource
     */
    public static FavoriteResource toResourceFromEntity(Favorite entity) {
        return new FavoriteResource(entity.getId(), entity.getUserId(), entity.getOfferId());
    }
}
