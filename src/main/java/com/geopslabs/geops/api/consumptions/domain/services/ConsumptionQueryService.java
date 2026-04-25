package com.geopslabs.geops.api.consumptions.domain.services;

import com.geopslabs.geops.api.consumptions.domain.model.aggregates.Consumption;
import com.geopslabs.geops.api.consumptions.domain.model.queries.GetConsumptionsByUserQuery;

import java.util.List;

public interface ConsumptionQueryService {
    List<Consumption> handle(GetConsumptionsByUserQuery query);
}
