package com.geopslabs.geops.api.reviews.infrastructure.persistence.jpa;

import com.geopslabs.geops.api.offers.domain.model.queries.GetOfferByIdQuery;
import com.geopslabs.geops.api.offers.domain.services.OfferQueryService;
import com.geopslabs.geops.api.reviews.domain.services.OfferQueryPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OfferQueryPortImpl implements OfferQueryPort {

    private final OfferQueryService offerQueryService;

    public OfferQueryPortImpl(OfferQueryService offerQueryService) {
        this.offerQueryService = offerQueryService;
    }

    @Override
    public boolean existsById(Long offerId) {
        return offerQueryService.handle(new GetOfferByIdQuery(offerId)).isPresent();
    }

    @Override
    public Optional<String> getTitleById(Long offerId) {
        return offerQueryService.handle(new GetOfferByIdQuery(offerId))
                .map(offer -> offer.getTitle());
    }
}
