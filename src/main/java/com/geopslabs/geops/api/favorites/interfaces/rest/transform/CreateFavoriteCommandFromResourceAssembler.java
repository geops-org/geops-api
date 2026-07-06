package com.geopslabs.geops.api.favorites.interfaces.rest.transform;

import com.geopslabs.geops.api.favorites.domain.model.commands.CreateFavoriteCommand;
import com.geopslabs.geops.api.favorites.interfaces.rest.resources.CreateFavoriteResource;

/**
 * CreateFavoriteCommandFromResourceAssembler
 * Assembler to transform a CreateFavoriteResource into a CreateFavoriteCommand.
 *
 * @summary Assembler for favorite creation commands
 * @since 5.0
 * @author GeOps Labs
 */
public class CreateFavoriteCommandFromResourceAssembler {

    /**
     * Transforms a resource and the path user id into a command
     * @param userId The user unique identifier from the request path
     * @param resource The favorite creation request data
     * @return The command to create the favorite
     */
    public static CreateFavoriteCommand toCommandFromResource(Long userId, CreateFavoriteResource resource) {
        return new CreateFavoriteCommand(userId, resource.offerId());
    }
}
