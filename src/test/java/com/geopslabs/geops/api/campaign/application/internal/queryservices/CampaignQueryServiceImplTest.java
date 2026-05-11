package com.geopslabs.geops.api.campaign.application.internal.queryservices;

import com.geopslabs.geops.api.campaign.domain.model.aggregates.Campaign;
import com.geopslabs.geops.api.campaign.domain.model.exceptions.CampaignUserNotFoundException;
import com.geopslabs.geops.api.campaign.domain.model.queries.GetAllCampaignsByUserIdQuery;
import com.geopslabs.geops.api.campaign.domain.model.queries.GetAllCampaignsQuery;
import com.geopslabs.geops.api.campaign.domain.model.queries.GetCampaignByIdQuery;
import com.geopslabs.geops.api.campaign.domain.services.UserExistenceChecker;
import com.geopslabs.geops.api.campaign.infrastructure.persistence.jpa.CampaignRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CampaignQueryServiceImplTest {

    @Mock private CampaignRepository   campaignRepository;
    @Mock private UserExistenceChecker userExistenceChecker;
    @InjectMocks private CampaignQueryServiceImpl service;

    // ── GetCampaignById

    @Test
    void handle_GetById_WhenCampaignNotFound_ThrowsException() {
        when(campaignRepository.findCampaignById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.handle(new GetCampaignByIdQuery(99L)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Campaign with id 99 not found");
    }

    @Test
    void handle_GetById_WhenCampaignFound_ReturnsCampaign() {
        Campaign campaign = mock(Campaign.class);
        when(campaignRepository.findCampaignById(1L)).thenReturn(Optional.of(campaign));

        var result = service.handle(new GetCampaignByIdQuery(1L));

        assertThat(result).isPresent().contains(campaign);
    }

    // ── GetAllCampaigns

    @Test
    void handle_GetAll_ReturnsCampaignList() {
        Campaign c1 = mock(Campaign.class);
        Campaign c2 = mock(Campaign.class);
        when(campaignRepository.findAll()).thenReturn(List.of(c1, c2));

        var result = service.handle(new GetAllCampaignsQuery());

        assertThat(result).hasSize(2).containsExactly(c1, c2);
    }

    @Test
    void handle_GetAll_WhenNoCampaigns_ReturnsEmptyList() {
        when(campaignRepository.findAll()).thenReturn(List.of());

        var result = service.handle(new GetAllCampaignsQuery());

        assertThat(result).isEmpty();
    }

    // ── GetAllCampaignsByUserId

    @Test
    void handle_GetByUserId_WhenUserDoesNotExist_ThrowsCampaignUserNotFoundException() {
        when(userExistenceChecker.existsById(42L)).thenReturn(false);

        assertThatThrownBy(() -> service.handle(new GetAllCampaignsByUserIdQuery(42L)))
                .isInstanceOf(CampaignUserNotFoundException.class)
                .hasMessageContaining("42");
    }

    @Test
    void handle_GetByUserId_WhenUserExists_ReturnsCampaignsForThatUser() {
        Campaign campaign = mock(Campaign.class);
        when(userExistenceChecker.existsById(1L)).thenReturn(true);
        when(campaignRepository.findAllByUserId(1L)).thenReturn(List.of(campaign));

        var result = service.handle(new GetAllCampaignsByUserIdQuery(1L));

        assertThat(result).hasSize(1).contains(campaign);
        verify(campaignRepository).findAllByUserId(1L);
    }

    @Test
    void handle_GetByUserId_WhenUserExistsButHasNoCampaigns_ReturnsEmptyList() {
        when(userExistenceChecker.existsById(1L)).thenReturn(true);
        when(campaignRepository.findAllByUserId(1L)).thenReturn(List.of());

        var result = service.handle(new GetAllCampaignsByUserIdQuery(1L));

        assertThat(result).isEmpty();
    }
}
