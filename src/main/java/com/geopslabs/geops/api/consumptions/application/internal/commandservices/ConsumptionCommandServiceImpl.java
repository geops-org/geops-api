package com.geopslabs.geops.api.consumptions.application.internal.commandservices;

import com.geopslabs.geops.api.consumptions.domain.model.aggregates.Consumption;
import com.geopslabs.geops.api.consumptions.domain.model.commands.RegisterVisitCommand;
import com.geopslabs.geops.api.consumptions.domain.services.ConsumptionCommandService;
import com.geopslabs.geops.api.consumptions.infrastructure.persistence.jpa.ConsumptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ConsumptionCommandServiceImpl implements ConsumptionCommandService {

    private final ConsumptionRepository consumptionRepository;

    public ConsumptionCommandServiceImpl(ConsumptionRepository consumptionRepository) {
        this.consumptionRepository = consumptionRepository;
    }

    @Override
    public Optional<Consumption> handle(RegisterVisitCommand command) {
        try {
            var consumption = new Consumption(command);
            return Optional.of(consumptionRepository.save(consumption));
        } catch (Exception e) {
            System.err.println("Error registering visit: " + e.getMessage());
            return Optional.empty();
        }
    }
}
