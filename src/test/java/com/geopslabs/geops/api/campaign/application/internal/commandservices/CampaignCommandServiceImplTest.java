package com.geopslabs.geops.api.campaign.application.internal.commandservices;

import com.geopslabs.geops.api.campaign.domain.model.aggregates.Campaign;
import com.geopslabs.geops.api.campaign.domain.model.commands.CreateCampaignCommand;
import com.geopslabs.geops.api.campaign.domain.model.commands.DeleteCampaignCommand;
import com.geopslabs.geops.api.campaign.domain.model.commands.UpdateCampaignCommand;
import com.geopslabs.geops.api.campaign.domain.model.exceptions.CampaignUserNotFoundException;
import com.geopslabs.geops.api.campaign.domain.model.exceptions.InvalidCampaignRoleException;
import com.geopslabs.geops.api.campaign.domain.services.UserExistenceChecker;
import com.geopslabs.geops.api.campaign.infrastructure.persistence.jpa.CampaignRepository;
import com.geopslabs.geops.api.shared.domain.model.valueobjects.ERole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CampaignCommandServiceImplTest {

    @Mock private CampaignRepository  campaignRepository;
    @Mock private UserExistenceChecker userExistenceChecker;
    @InjectMocks private CampaignCommandServiceImpl service;

    private static final ZoneId LIMA      = ZoneId.of("America/Lima");
    private static final LocalDate FUTURE = LocalDate.now(LIMA).plusDays(1);
    private static final LocalDate LATER  = LocalDate.now(LIMA).plusDays(7);

    private CreateCampaignCommand ownerCommand() {
        return new CreateCampaignCommand(1L, ERole.OWNER, "Campaign", "Desc", FUTURE, LATER, 500f);
    }

    private CreateCampaignCommand consumerCommand() {
        return new CreateCampaignCommand(1L, ERole.CONSUMER, "Campaign", "Desc", FUTURE, LATER, 500f);
    }

    private UpdateCampaignCommand validUpdate(Long id) {
        return new UpdateCampaignCommand(id, "Updated", "Updated desc",
                FUTURE, LATER, "ACTIVE", null, null, null);
    }

    // ── Create

    @Test
    void handle_Create_WhenNotOwnerRole_ThrowsInvalidCampaignRoleException() {
        assertThatThrownBy(() -> service.handle(consumerCommand()))
                .isInstanceOf(InvalidCampaignRoleException.class);
        verify(campaignRepository, never()).save(any());
    }

    @Test
    void handle_Create_WhenUserDoesNotExist_ThrowsCampaignUserNotFoundException() {
        when(userExistenceChecker.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> service.handle(ownerCommand()))
                .isInstanceOf(CampaignUserNotFoundException.class);
        verify(campaignRepository, never()).save(any());
    }

    @Test
    void handle_Create_WithValidOwnerAndUser_SavesAndReturnsCampaign() {
        when(userExistenceChecker.existsById(1L)).thenReturn(true);
        Campaign saved = mock(Campaign.class);
        when(campaignRepository.save(any(Campaign.class))).thenReturn(saved);

        var result = service.handle(ownerCommand());

        assertThat(result).isPresent().contains(saved);
        verify(campaignRepository).save(any(Campaign.class));
    }

    // ── Update

    @Test
    void handle_Update_WhenCampaignNotFound_ReturnsEmpty() {
        when(campaignRepository.findCampaignById(99L)).thenReturn(Optional.empty());

        var result = service.handle(validUpdate(99L));

        assertThat(result).isEmpty();
    }

    @Test
    void handle_Update_WhenCampaignFound_UpdatesAndReturnsCampaign() {
        Campaign existing = mock(Campaign.class);
        when(campaignRepository.findCampaignById(1L)).thenReturn(Optional.of(existing));
        when(campaignRepository.save(existing)).thenReturn(existing);

        var result = service.handle(validUpdate(1L));

        assertThat(result).isPresent();
        verify(existing).edit(any(), any(), any(), any(), any(), any(), any(), any());
        verify(campaignRepository).save(existing);
    }

    // ── Delete

    @Test
    void handle_Delete_WhenCampaignNotFound_ReturnsFalse() {
        when(campaignRepository.findCampaignById(99L)).thenReturn(Optional.empty());

        var result = service.handle(new DeleteCampaignCommand(99L));

        assertThat(result).isFalse();
        verify(campaignRepository, never()).deleteCampaignById(any());
    }

    @Test
    void handle_Delete_WhenCampaignFound_DeletesAndReturnsTrue() {
        Campaign existing = mock(Campaign.class);
        when(campaignRepository.findCampaignById(1L)).thenReturn(Optional.of(existing));

        var result = service.handle(new DeleteCampaignCommand(1L));

        assertThat(result).isTrue();
        verify(campaignRepository).deleteCampaignById(1L);
    }
}
