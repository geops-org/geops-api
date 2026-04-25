package com.geopslabs.geops.api.consumptions.interfaces.rest.transform;

import com.geopslabs.geops.api.consumptions.domain.model.aggregates.Consumption;
import com.geopslabs.geops.api.consumptions.interfaces.rest.resources.ConsumptionResource;

public class ConsumptionResourceFromEntityAssembler {

    public static ConsumptionResource toResourceFromEntity(Consumption entity) {
        return new ConsumptionResource(
            entity.getId(),
            entity.getUserId(),
            entity.getOfferId(),
            entity.getNotes(),
            entity.getCreatedAt()
        );
    }
}
