package com.geopslabs.geops.api.campaign.interfaces.rest.transform;

import com.geopslabs.geops.api.campaign.domain.model.aggregates.Campaign;
import com.geopslabs.geops.api.campaign.interfaces.rest.resources.CampaignResource;

public class CampaignResourceFromEntityAssembler {
    public static CampaignResource toResourceFromEntity(Campaign entity)
    {
        return new CampaignResource(entity.getId(), entity.getUserId(), entity.getName(), entity.getDescription(),entity.getStartDate(),
                entity.getEndDate(), entity.getStatus().toString(), entity.getEstimatedBudget(),
                entity.getTotalImpressions(),entity.getTotalClicks(),entity.getCTR(),entity.getCreatedAt(),
                entity.getUpdatedAt());
    }
}
