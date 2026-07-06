package com.geopslabs.geops.api.favorites.domain.model.commands;

/**
 * CreateFavoriteCommand
 * Command record to save an offer as favorite for a user.
 *
 * @param userId The user unique identifier
 * @param offerId The offer unique identifier
 * @summary Command to create a favorite
 * @since 5.0
 * @author GeOps Labs
 */
public record CreateFavoriteCommand(Long userId, Long offerId) {
    public CreateFavoriteCommand {
        if (userId == null || userId < 1)
            throw new IllegalArgumentException("User Id cannot be null or less than 1");
        if (offerId == null || offerId < 1)
            throw new IllegalArgumentException("Offer Id cannot be null or less than 1");
    }
}
