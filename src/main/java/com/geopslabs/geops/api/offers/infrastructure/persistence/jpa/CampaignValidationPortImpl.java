package com.geopslabs.geops.api.offers.infrastructure.persistence.jpa;

import com.geopslabs.geops.api.campaign.domain.model.queries.GetCampaignByIdQuery;
import com.geopslabs.geops.api.campaign.domain.model.valueobjects.ECampaignStatus;
import com.geopslabs.geops.api.campaign.domain.services.CampaignQueryService;
import com.geopslabs.geops.api.offers.domain.services.CampaignValidationPort;
import org.springframework.stereotype.Component;

@Component
public class CampaignValidationPortImpl implements CampaignValidationPort {

    private final CampaignQueryService campaignQueryService;

    public CampaignValidationPortImpl(CampaignQueryService campaignQueryService) {
        this.campaignQueryService = campaignQueryService;
    }

    @Override
    public boolean existsById(Long campaignId) {
        return campaignQueryService.handle(new GetCampaignByIdQuery(campaignId)).isPresent();
    }

    @Override
    public boolean isActive(Long campaignId) {
        return campaignQueryService.handle(new GetCampaignByIdQuery(campaignId))
                .map(c -> ECampaignStatus.ACTIVE.equals(c.getStatus()))
                .orElse(false);
    }
}
