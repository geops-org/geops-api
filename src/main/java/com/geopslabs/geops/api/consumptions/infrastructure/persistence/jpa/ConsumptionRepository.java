package com.geopslabs.geops.api.consumptions.infrastructure.persistence.jpa;

import com.geopslabs.geops.api.consumptions.domain.model.aggregates.Consumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsumptionRepository extends JpaRepository<Consumption, Long> {

    List<Consumption> findByUserId(Long userId);

    boolean existsByUserIdAndOfferId(Long userId, Long offerId);
}
