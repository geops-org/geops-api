package com.geopslabs.geops.api.campaign.domain.model.commands;

import com.geopslabs.geops.api.shared.domain.model.valueobjects.ERole;

import java.time.LocalDate;
import java.time.ZoneId;

public record CreateCampaignCommand(Long userId, ERole requesterRole, String name, String description,
                                    LocalDate startDate, LocalDate endDate, Float estimatedBudget) {

    private static final float MAX_BUDGET = 1_000_000f;
    private static final ZoneId LIMA_ZONE = ZoneId.of("America/Lima");

    public CreateCampaignCommand {
        if (userId == null || userId < 0)
            throw new IllegalArgumentException("userId cannot be null");

        if (requesterRole == null)
            throw new IllegalArgumentException("requesterRole cannot be null");

        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Campaign name cannot be null or blank");

        if (description == null || description.isBlank())
            throw new IllegalArgumentException("Campaign description cannot be null or blank");

        if (startDate == null)
            throw new IllegalArgumentException("Start date cannot be null");

        if (startDate.isBefore(LocalDate.now(LIMA_ZONE)))
            throw new IllegalArgumentException("Start date must be today or in the future");

        if (endDate == null)
            throw new IllegalArgumentException("End date cannot be null");

        if (endDate.isBefore(startDate))
            throw new IllegalArgumentException("End date cannot be before start date");

        if (estimatedBudget != null && estimatedBudget > MAX_BUDGET)
            throw new IllegalArgumentException("Estimated budget cannot exceed " + MAX_BUDGET);

    }
}
