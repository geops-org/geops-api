package com.geopslabs.geops.api.campaign.domain.model.commands;

import com.geopslabs.geops.api.campaign.domain.model.aggregates.Campaign;
import com.geopslabs.geops.api.shared.domain.model.valueobjects.ERole;

import java.time.LocalDate;

/**
 * Command to create a campaign
 * @param userId The user id
 * @param name The campaign name
 * @param description The campaign description
 * @param startDate The start date of the campaign
 * @param endDate The end date of the campaign
 * @param estimatedBudget The estimated budget for the campaign
 * @see Campaign
 */
public record CreateCampaignCommand(Long userId, ERole requesterRole, String name, String description, LocalDate startDate, LocalDate endDate, Float estimatedBudget) {
    public CreateCampaignCommand {
        if(userId == null || userId < 0)
            throw new IllegalArgumentException("userId cannot be null");

        if (requesterRole == null)
            throw new IllegalArgumentException("requesterRole cannot be null");

        if(name == null || name.isBlank())
            throw new IllegalArgumentException("Campaign name cannot be null or blank");

        if(description == null || description.isBlank())
            throw new IllegalArgumentException("Campaign description cannot be null or blank");

        if(startDate == null)
            throw new IllegalArgumentException("Start date cannot be null");

        if(endDate == null)
            throw new IllegalArgumentException("End date cannot be null");

        if(endDate.isBefore(startDate))
            throw new IllegalArgumentException("End date cannot be before start date");
    }
}
