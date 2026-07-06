package com.geopslabs.geops.api.reviews.interfaces.rest.transform;

import com.geopslabs.geops.api.reviews.domain.model.commands.CreateReviewCommand;
import com.geopslabs.geops.api.reviews.interfaces.rest.resources.CreateReviewResource;

/**
 * CreateReviewCommandFromResourceAssembler
 * Assembler to transform a CreateReviewResource into a CreateReviewCommand.
 *
 * @summary Assembler for review creation commands
 * @since 5.0
 * @author GeOps Labs
 */
public class CreateReviewCommandFromResourceAssembler {

    /**
     * Transforms a resource and the path offer id into a command
     * @param offerId The offer unique identifier from the request path
     * @param resource The review creation request data
     * @return The command to create the review
     */
    public static CreateReviewCommand toCommandFromResource(Long offerId, CreateReviewResource resource) {
        return new CreateReviewCommand(resource.userId(), offerId, resource.rating(), resource.comment());
    }
}
