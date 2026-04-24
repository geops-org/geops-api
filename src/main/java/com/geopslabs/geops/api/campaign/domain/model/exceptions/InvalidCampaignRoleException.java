package com.geopslabs.geops.api.campaign.domain.model.exceptions;

public class InvalidCampaignRoleException extends RuntimeException {
    public InvalidCampaignRoleException() {
        super("Only OWNER users can create campaigns");
    }
}
