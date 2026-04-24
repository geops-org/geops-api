package com.geopslabs.geops.api.campaign.interfaces.rest.transform;

import com.geopslabs.geops.api.campaign.domain.model.commands.UpdateCampaignCommand;
import com.geopslabs.geops.api.campaign.interfaces.rest.resources.UpdateCampaignResource;

public class UpdateCampaignCommandFromResourceAssembler {
    public static UpdateCampaignCommand toCommandFromResource(Long id, UpdateCampaignResource resource) {
        return new UpdateCampaignCommand(id, resource.name(), resource.description(), resource.startDate(),
                resource.endDate(), resource.status(), resource.estimatedBudget(),
                resource.totalImpressions(), resource.totalClicks(), resource.ctr());
    }
}
