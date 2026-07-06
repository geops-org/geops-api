package com.geopslabs.geops.api.favorites.application.internal.commandservices;

import com.geopslabs.geops.api.favorites.domain.model.aggregates.Favorite;
import com.geopslabs.geops.api.favorites.domain.model.commands.CreateFavoriteCommand;
import com.geopslabs.geops.api.favorites.domain.model.commands.DeleteFavoriteCommand;
import com.geopslabs.geops.api.favorites.domain.services.FavoriteCommandService;
import com.geopslabs.geops.api.favorites.infrastructure.persistence.jpa.FavoriteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * FavoriteCommandServiceImpl
 * Implementation of the FavoriteCommandService that handles all command operations
 * for favorites. This service implements the business rule that a user can save
 * an offer as favorite only once, following DDD principles.
 *
 * @summary Implementation of favorite command service operations
 * @since 5.0
 * @author GeOps Labs
 */
@Service
@Transactional
public class FavoriteCommandServiceImpl implements FavoriteCommandService {

    private final FavoriteRepository favoriteRepository;

    public FavoriteCommandServiceImpl(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Favorite> handle(CreateFavoriteCommand command) {
        try {
            if (favoriteRepository.existsByUserIdAndOfferId(command.userId(), command.offerId()))
                throw new IllegalArgumentException(
                        "Offer with id " + command.offerId() + " is already a favorite for user " + command.userId());

            var favorite = new Favorite(command);
            favoriteRepository.save(favorite);
            return Optional.of(favorite);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean handle(DeleteFavoriteCommand command) {
        var favorite = favoriteRepository.findByUserIdAndOfferId(command.userId(), command.offerId());
        if (favorite.isEmpty()) return false;
        favoriteRepository.delete(favorite.get());
        return true;
    }
}
