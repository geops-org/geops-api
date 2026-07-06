package com.geopslabs.geops.api.reviews.interfaces.rest.transform;

import com.geopslabs.geops.api.reviews.domain.model.aggregates.Review;
import com.geopslabs.geops.api.reviews.interfaces.rest.resources.ReviewResource;

/**
 * ReviewResourceFromEntityAssembler
 * Assembler to transform a Review entity into a ReviewResource.
 *
 * @summary Assembler for review REST resources
 * @since 5.0
 * @author GeOps Labs
 */
public class ReviewResourceFromEntityAssembler {

    /**
     * Transforms an entity into a REST resource
     * @param entity The review entity
     * @return The review resource
     */
    public static ReviewResource toResourceFromEntity(Review entity) {
        return new ReviewResource(entity.getId(), entity.getUserId(), entity.getOfferId(),
                entity.getRating(), entity.getComment());
    }
}
