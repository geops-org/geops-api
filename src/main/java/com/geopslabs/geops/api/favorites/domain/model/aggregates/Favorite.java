package com.geopslabs.geops.api.favorites.domain.model.aggregates;

import com.geopslabs.geops.api.favorites.domain.model.commands.CreateFavoriteCommand;
import com.geopslabs.geops.api.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;

/**
 * Favorite Aggregate Root
 *
 * This aggregate represents a user's favorite offer in the GeOps platform.
 * It manages the many-to-many relationship between users and offers,
 * allowing users to mark offers as favorites for quick access
 *
 * @summary Manages user favorite offers (heart button functionality)
 * @since 1.0
 * @author GeOps Labs
 */
@Entity
@Table(name = "favorites",
    uniqueConstraints = @UniqueConstraint(
        name = "uk_user_offer",
        columnNames = {"user_id", "offer_id"}
    ),
    indexes = {
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_offer_id", columnList = "offer_id")
    }
)
@Getter
public class Favorite extends AuditableAbstractAggregateRoot<Favorite> {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "offer_id", nullable = false)
    private Long offerId;

    /**
     * Default constructor for JPA
     */
    protected Favorite() {}

    public Favorite(CreateFavoriteCommand command) {
        this.userId = command.userId();
        this.offerId = command.offerId();
    }

    public boolean belongsToUser(Long userId) {
        return this.userId != null && this.userId.equals(userId);
    }

    public boolean isForOffer(Long offerId) {
        return this.offerId != null && this.offerId.equals(offerId);
    }
}
