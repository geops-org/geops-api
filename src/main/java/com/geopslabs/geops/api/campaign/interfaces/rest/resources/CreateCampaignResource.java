package com.geopslabs.geops.api.campaign.interfaces.rest.resources;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateCampaignResource(
        @NotNull Long userId,
        @NotBlank String name,
        @NotBlank String description,
        @NotNull @FutureOrPresent LocalDate startDate,
        @NotNull LocalDate endDate,
        Float estimatedBudget
) {
    @AssertTrue(message = "End date must be after start date")
    public boolean isEndDateAfterStartDate() {
        return startDate == null || endDate == null || !endDate.isBefore(startDate);
    }
}
