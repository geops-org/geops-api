package com.geopslabs.geops.api.campaign.domain.model.exceptions;

public class CampaignUserNotFoundException extends RuntimeException {
    public CampaignUserNotFoundException(Long userId) {
        super("User with id " + userId + " was not found");
    }
}
