package com.geopslabs.geops.api.campaign.infrastructure.persistence.jpa;

import com.geopslabs.geops.api.campaign.domain.model.aggregates.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    Optional<Campaign> findCampaignById(Long id);

    void deleteCampaignById(Long id);

    List<Campaign> findAllByUserId(Long userId);
}
