package com.geopslabs.geops.api.favorites.domain.model.commands;

/**
 * DeleteFavoriteCommand
 * Command record to remove an offer from a user's favorites.
 *
 * @param userId The user unique identifier
 * @param offerId The offer unique identifier
 * @summary Command to delete a favorite
 * @since 5.0
 * @author GeOps Labs
 */
public record DeleteFavoriteCommand(Long userId, Long offerId) {
    public DeleteFavoriteCommand {
        if (userId == null || userId < 1)
            throw new IllegalArgumentException("User Id cannot be null or less than 1");
        if (offerId == null || offerId < 1)
            throw new IllegalArgumentException("Offer Id cannot be null or less than 1");
    }
}
