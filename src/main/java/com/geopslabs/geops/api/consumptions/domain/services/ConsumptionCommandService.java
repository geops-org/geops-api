package com.geopslabs.geops.api.consumptions.domain.services;

import com.geopslabs.geops.api.consumptions.domain.model.aggregates.Consumption;
import com.geopslabs.geops.api.consumptions.domain.model.commands.RegisterVisitCommand;

import java.util.Optional;

public interface ConsumptionCommandService {
    Optional<Consumption> handle(RegisterVisitCommand command);
}
