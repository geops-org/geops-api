package com.geopslabs.geops.api.reviews.infrastructure.persistence.jpa;

import com.geopslabs.geops.api.consumptions.domain.model.queries.GetConsumptionsByUserQuery;
import com.geopslabs.geops.api.consumptions.domain.services.ConsumptionQueryService;
import com.geopslabs.geops.api.reviews.domain.services.ConsumptionValidationPort;
import org.springframework.stereotype.Component;

@Component
public class ConsumptionValidationPortImpl implements ConsumptionValidationPort {

    private final ConsumptionQueryService consumptionQueryService;

    public ConsumptionValidationPortImpl(ConsumptionQueryService consumptionQueryService) {
        this.consumptionQueryService = consumptionQueryService;
    }

    @Override
    public boolean existsByUserAndOffer(Long userId, Long offerId) {
        var consumptions = consumptionQueryService.handle(new GetConsumptionsByUserQuery(userId));
        return consumptions.stream().anyMatch(c -> offerId.equals(c.getOfferId()));
    }
}
