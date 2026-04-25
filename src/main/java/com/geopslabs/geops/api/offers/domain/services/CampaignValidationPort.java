package com.geopslabs.geops.api.offers.domain.services;

public interface CampaignValidationPort {
    boolean existsById(Long campaignId);
    boolean isActive(Long campaignId);
}
