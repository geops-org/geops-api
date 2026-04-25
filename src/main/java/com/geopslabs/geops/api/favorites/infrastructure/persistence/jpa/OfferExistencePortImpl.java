package com.geopslabs.geops.api.favorites.infrastructure.persistence.jpa;

import com.geopslabs.geops.api.offers.domain.model.queries.GetOfferByIdQuery;
import com.geopslabs.geops.api.offers.domain.services.OfferQueryService;
import com.geopslabs.geops.api.favorites.domain.services.OfferExistencePort;
import org.springframework.stereotype.Component;

@Component
public class OfferExistencePortImpl implements OfferExistencePort {

    private final OfferQueryService offerQueryService;

    public OfferExistencePortImpl(OfferQueryService offerQueryService) {
        this.offerQueryService = offerQueryService;
    }

    @Override
    public boolean existsById(Long offerId) {
        return offerQueryService.handle(new GetOfferByIdQuery(offerId)).isPresent();
    }
}
