package com.geopslabs.geops.api.favorites.application.internal.queryservices;

import com.geopslabs.geops.api.favorites.domain.model.aggregates.Favorite;
import com.geopslabs.geops.api.favorites.domain.model.queries.GetFavoritesByUserIdQuery;
import com.geopslabs.geops.api.favorites.domain.services.FavoriteQueryService;
import com.geopslabs.geops.api.favorites.infrastructure.persistence.jpa.FavoriteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * FavoriteQueryServiceImpl
 * Implementation of the FavoriteQueryService that handles all query operations
 * for favorites following CQRS design.
 *
 * @summary Implementation of favorite query service operations
 * @since 5.0
 * @author GeOps Labs
 */
@Service
public class FavoriteQueryServiceImpl implements FavoriteQueryService {

    private final FavoriteRepository favoriteRepository;

    public FavoriteQueryServiceImpl(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Favorite> handle(GetFavoritesByUserIdQuery query) {
        return favoriteRepository.findByUserId(query.userId());
    }
}
