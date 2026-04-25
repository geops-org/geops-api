package com.geopslabs.geops.api.consumptions.domain.model.commands;

public record RegisterVisitCommand(Long userId, Long offerId, String notes) {}
