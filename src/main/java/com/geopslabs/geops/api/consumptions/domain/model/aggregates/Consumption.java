package com.geopslabs.geops.api.consumptions.domain.model.aggregates;

import com.geopslabs.geops.api.consumptions.domain.model.commands.RegisterVisitCommand;
import com.geopslabs.geops.api.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "consumptions", indexes = {
    @Index(name = "idx_consumption_user_id", columnList = "user_id"),
    @Index(name = "idx_consumption_offer_id", columnList = "offer_id")
})
@Getter
public class Consumption extends AuditableAbstractAggregateRoot<Consumption> {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "offer_id", nullable = false)
    private Long offerId;

    @Column(name = "notes", length = 500)
    private String notes;

    protected Consumption() {}

    public Consumption(RegisterVisitCommand command) {
        this.userId = command.userId();
        this.offerId = command.offerId();
        this.notes = command.notes();
    }
}
