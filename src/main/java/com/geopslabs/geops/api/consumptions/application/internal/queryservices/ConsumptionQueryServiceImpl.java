package com.geopslabs.geops.api.consumptions.application.internal.queryservices;

import com.geopslabs.geops.api.consumptions.domain.model.aggregates.Consumption;
import com.geopslabs.geops.api.consumptions.domain.model.queries.GetConsumptionsByUserQuery;
import com.geopslabs.geops.api.consumptions.domain.services.ConsumptionQueryService;
import com.geopslabs.geops.api.consumptions.infrastructure.persistence.jpa.ConsumptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ConsumptionQueryServiceImpl implements ConsumptionQueryService {

    private final ConsumptionRepository consumptionRepository;

    public ConsumptionQueryServiceImpl(ConsumptionRepository consumptionRepository) {
        this.consumptionRepository = consumptionRepository;
    }

    @Override
    public List<Consumption> handle(GetConsumptionsByUserQuery query) {
        return consumptionRepository.findByUserId(query.userId());
    }
}
