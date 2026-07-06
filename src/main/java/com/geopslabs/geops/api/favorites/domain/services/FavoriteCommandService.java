package com.geopslabs.geops.api.favorites.domain.services;

import com.geopslabs.geops.api.favorites.domain.model.aggregates.Favorite;
import com.geopslabs.geops.api.favorites.domain.model.commands.CreateFavoriteCommand;
import com.geopslabs.geops.api.favorites.domain.model.commands.DeleteFavoriteCommand;

import java.util.Optional;

/**
 * FavoriteCommandService
 * Domain service interface that defines command operations for favorites.
 * This service handles all write operations following CQRS design.
 *
 * @summary Service interface for handling favorite command operations
 * @since 5.0
 * @author GeOps Labs
 */
public interface FavoriteCommandService {

    /**
     * Handles the creation of a favorite for a user
     * @param command The command containing user and offer identifiers
     * @return The created favorite, or empty if the operation failed
     */
    Optional<Favorite> handle(CreateFavoriteCommand command);

    /**
     * Handles the removal of a favorite for a user
     * @param command The command containing user and offer identifiers
     * @return true if the favorite was removed, false otherwise
     */
    boolean handle(DeleteFavoriteCommand command);
}
