package com.geopslabs.geops.api.consumptions.interfaces.rest.resources;

import java.util.Date;

public record ConsumptionResource(Long id, Long userId, Long offerId, String notes, Date createdAt) {}
