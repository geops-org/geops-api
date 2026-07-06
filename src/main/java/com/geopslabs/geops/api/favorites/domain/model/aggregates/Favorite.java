package com.geopslabs.geops.api.favorites.domain.model.aggregates;

import com.geopslabs.geops.api.favorites.domain.model.commands.CreateFavoriteCommand;
import com.geopslabs.geops.api.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;

/**
 * Favorite Aggregate Root
 *
 * This aggregate represents a favorite offer saved by a consumer in the GeOps platform.
 * It links a user with an offer they want to keep for later, enabling quick access
 * and shareable recommendations.
 *
 * @summary Manages favorite offers saved by consumers
 * @since 5.0
 * @author GeOps Labs
 */
@Entity
@Table(name = "favorites", uniqueConstraints = {
        @UniqueConstraint(name = "uk_favorite_user_offer", columnNames = {"user_id", "offer_id"})
})
@Getter
public class Favorite extends AuditableAbstractAggregateRoot<Favorite> {

    /**
     * Identifier of the user that saved the offer
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * Identifier of the saved offer
     */
    @Column(name = "offer_id", nullable = false)
    private Long offerId;

    /**
     * Default constructor required by JPA
     */
    protected Favorite() {
    }

    /**
     * Creates a Favorite from a CreateFavoriteCommand
     *
     * @param command The command containing the user and offer identifiers
     */
    public Favorite(CreateFavoriteCommand command) {
        this.userId = command.userId();
        this.offerId = command.offerId();
    }
}
