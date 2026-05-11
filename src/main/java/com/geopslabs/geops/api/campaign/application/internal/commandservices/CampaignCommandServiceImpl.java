package com.geopslabs.geops.api.campaign.application.internal.commandservices;

import com.geopslabs.geops.api.campaign.domain.model.aggregates.Campaign;
import com.geopslabs.geops.api.campaign.domain.model.commands.CreateCampaignCommand;
import com.geopslabs.geops.api.campaign.domain.model.commands.DeleteCampaignCommand;
import com.geopslabs.geops.api.campaign.domain.model.commands.UpdateCampaignCommand;
import com.geopslabs.geops.api.campaign.domain.model.exceptions.CampaignUserNotFoundException;
import com.geopslabs.geops.api.campaign.domain.model.exceptions.InvalidCampaignRoleException;
import com.geopslabs.geops.api.campaign.domain.model.valueobjects.ECampaignStatus;
import com.geopslabs.geops.api.campaign.domain.services.CampaignCommandService;
import com.geopslabs.geops.api.campaign.domain.services.UserExistenceChecker;
import com.geopslabs.geops.api.campaign.infrastructure.persistence.jpa.CampaignRepository;
import com.geopslabs.geops.api.shared.domain.model.valueobjects.ERole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CampaignCommandServiceImpl implements CampaignCommandService {

    private final CampaignRepository campaignRepository;
    private final UserExistenceChecker userExistenceChecker;

    public CampaignCommandServiceImpl(CampaignRepository campaignRepository, UserExistenceChecker userExistenceChecker) {
        this.campaignRepository = campaignRepository;
        this.userExistenceChecker = userExistenceChecker;
    }

    @Override
    public Optional<Campaign> handle(CreateCampaignCommand command) {
        if (!ERole.OWNER.equals(command.requesterRole())) {
            throw new InvalidCampaignRoleException();
        }

        if (!userExistenceChecker.existsById(command.userId())) {
            throw new CampaignUserNotFoundException(command.userId());
        }

        var campaign = new Campaign(command);
        return Optional.of(campaignRepository.save(campaign));
    }

    @Override
    public Optional<Campaign> handle(UpdateCampaignCommand command) {
        var foundCampaign = campaignRepository.findCampaignById(command.id());
        if (foundCampaign.isEmpty()) return Optional.empty();
        foundCampaign.get().edit(command.name(), command.description(), command.startDate(), command.endDate(),
                ECampaignStatus.valueOf(command.status()), command.estimatedBudget(), command.totalImpressions(),
                command.totalClicks());
        return Optional.of(campaignRepository.save(foundCampaign.get()));
    }

    @Override
    public boolean handle(DeleteCampaignCommand command) {
        var foundCampaign = campaignRepository.findCampaignById(command.id());
        if (foundCampaign.isEmpty()) return false;
        campaignRepository.deleteCampaignById(command.id());
        return true;
    }


}
