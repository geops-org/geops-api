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

import java.util.NoSuchElementException;
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
        try{
            var foundCampaign = campaignRepository.findCampaignById(command.id());
            if(foundCampaign.isEmpty()) throw new NoSuchElementException("Campaign not found with id: " + command.id());
                foundCampaign.get().edit(command.name(), command.description(), command.startDate(), command.endDate(),
                    ECampaignStatus.valueOf(command.status()), command.estimatedBudget(), command.totalImpressions(),
                    command.totalClicks());
            var editedCampaign = campaignRepository.save(foundCampaign.get());
            return Optional.of(editedCampaign);
        }
        catch(Exception e){
            System.out.println("Error editing campaign: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public boolean handle(DeleteCampaignCommand command) {
        try{
            var foundCampaign = campaignRepository.findCampaignById(command.id());
            if(foundCampaign.isEmpty()) throw new NoSuchElementException("Campaign not found with id: " + command.id());
            campaignRepository.deleteCampaignById(command.id());
            return true;
        }
        catch(Exception e){
            System.out.println("Error deleting campaign: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


}
