package com.geopslabs.geops.api.campaign.interfaces.rest;

import com.geopslabs.geops.api.campaign.domain.model.exceptions.CampaignUserNotFoundException;
import com.geopslabs.geops.api.campaign.domain.model.exceptions.InvalidCampaignRoleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = CampaignController.class)
public class CampaignExceptionHandler {

    @ExceptionHandler(InvalidCampaignRoleException.class)
    public ResponseEntity<CampaignErrorResponse> handleInvalidRole(InvalidCampaignRoleException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new CampaignErrorResponse("CAMPAIGN_ROLE_FORBIDDEN", exception.getMessage()));
    }

    @ExceptionHandler(CampaignUserNotFoundException.class)
    public ResponseEntity<CampaignErrorResponse> handleUserNotFound(CampaignUserNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new CampaignErrorResponse("CAMPAIGN_USER_NOT_FOUND", exception.getMessage()));
    }
}
