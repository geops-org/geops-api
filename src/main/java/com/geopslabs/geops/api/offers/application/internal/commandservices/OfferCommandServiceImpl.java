package com.geopslabs.geops.api.offers.application.internal.commandservices;

import com.geopslabs.geops.api.offers.domain.model.aggregates.Offer;
import com.geopslabs.geops.api.offers.domain.model.commands.CreateOfferCommand;
import com.geopslabs.geops.api.offers.domain.model.commands.DeleteOfferCommand;
import com.geopslabs.geops.api.offers.domain.model.commands.UpdateOfferCommand;
import com.geopslabs.geops.api.offers.domain.services.CampaignValidationPort;
import com.geopslabs.geops.api.offers.domain.services.OfferCommandService;
import com.geopslabs.geops.api.offers.infrastructure.persistence.jpa.OfferRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * OfferCommandServiceImpl
 * Implementation of the OfferCommandService that handles all command operations
 * for offers. This service implements the business logic for
 * creating, updating, and managing offers following DDD principles
 *
 * @summary Implementation of offer command service operations
 * @since 1.0
 * @author GeOps Labs
 */
@Service
@Transactional
public class OfferCommandServiceImpl implements OfferCommandService {

    private final OfferRepository offerRepository;
    private final CampaignValidationPort campaignValidationPort;

    public OfferCommandServiceImpl(OfferRepository offerRepository, CampaignValidationPort campaignValidationPort) {
        this.offerRepository = offerRepository;
        this.campaignValidationPort = campaignValidationPort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Offer> handle(CreateOfferCommand command) {
        try {
            if (!campaignValidationPort.existsById(command.campaignId()))
                throw new IllegalArgumentException("Campaign with id " + command.campaignId() + " does not exist");

            if (!campaignValidationPort.isActive(command.campaignId()))
                throw new IllegalArgumentException("The Campaign is not ACTIVE");

            var offer = new Offer(command.campaignId(), command);

            // Save the offer to the repository
            var savedOffer = offerRepository.save(offer);

            return Optional.of(savedOffer);

        } catch (Exception e) {
            // Log the error with full stacktrace
            System.err.println("Error creating offer: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Offer> handle(UpdateOfferCommand command) {
        try {
            // Find the existing offer by ID
            var existingOfferOpt = offerRepository.findById(command.id());

            if (existingOfferOpt.isEmpty()) {
                return Optional.empty();
            }

            var existingOffer = existingOfferOpt.get();

            // Update the offer with new data
            existingOffer.updateOffer(command);

            // Save the updated offer (this should trigger @PreUpdate)
            var updatedOffer = offerRepository.save(existingOffer);

            return Optional.of(updatedOffer);

        } catch (Exception e) {
            // Log the error with full stacktrace
            System.err.println("Error updating offer: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean handle(DeleteOfferCommand command) {
        if (command.id() == null || command.id() <= 0) {
            throw new IllegalArgumentException("id cannot be null or negative");
        }

        try {
            // First check if offer exists
            if (!offerRepository.existsById(command.id())) {
                return false;
            }

            // Delete the offer
            offerRepository.deleteById(command.id());
            return true;

        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error deleting offer: " + e.getMessage());
            return false;
        }
    }
}
