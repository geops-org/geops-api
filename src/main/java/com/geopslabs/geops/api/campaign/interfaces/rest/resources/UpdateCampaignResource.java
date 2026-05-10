package com.geopslabs.geops.api.campaign.interfaces.rest.resources;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UpdateCampaignResource(
        @NotBlank String name,
        @NotBlank String description,
        @NotNull LocalDate startDate,
        @NotNull LocalDate endDate,
        @NotBlank String status,
        Float estimatedBudget,
        Long totalImpressions,
        Long totalClicks
) {
    @AssertTrue(message = "End date must be after start date")
    public boolean isEndDateAfterStartDate() {
        return startDate == null || endDate == null || !endDate.isBefore(startDate);
    }
}
