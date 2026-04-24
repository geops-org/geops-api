package com.geopslabs.geops.api.campaign.interfaces.rest.transform;

import com.geopslabs.geops.api.campaign.domain.model.commands.CreateCampaignCommand;
import com.geopslabs.geops.api.campaign.interfaces.rest.resources.CreateCampaignResource;
import com.geopslabs.geops.api.shared.domain.model.valueobjects.ERole;

public class CreateCampaignCommandFromResourceAssembler {
    public static CreateCampaignCommand toCommandFromResource(CreateCampaignResource resource, ERole requesterRole) {
        return new CreateCampaignCommand(resource.userId(), requesterRole, resource.name(), resource.description(), resource.startDate(),
                resource.endDate(), resource.estimatedBudget());
    }
}
