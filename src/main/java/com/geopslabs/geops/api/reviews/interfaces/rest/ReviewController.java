package com.geopslabs.geops.api.reviews.interfaces.rest;

import com.geopslabs.geops.api.reviews.domain.model.queries.GetReviewsByOfferIdQuery;
import com.geopslabs.geops.api.reviews.domain.services.ReviewCommandService;
import com.geopslabs.geops.api.reviews.domain.services.ReviewQueryService;
import com.geopslabs.geops.api.reviews.interfaces.rest.resources.CreateReviewResource;
import com.geopslabs.geops.api.reviews.interfaces.rest.resources.ReviewResource;
import com.geopslabs.geops.api.reviews.interfaces.rest.transform.CreateReviewCommandFromResourceAssembler;
import com.geopslabs.geops.api.reviews.interfaces.rest.transform.ReviewResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * ReviewController
 * REST controller that exposes review endpoints for the GeOps platform.
 * This controller handles HTTP requests to create and list reviews of an offer.
 *
 * @summary REST controller for review operations
 * @since 5.0
 * @author GeOps Labs
 */
@Tag(name = "Reviews", description = "Offer reviews operations and management")
@RestController
@RequestMapping(value = "/api/v1/offers/{offerId}/reviews", produces = APPLICATION_JSON_VALUE)
public class ReviewController {

    private final ReviewCommandService reviewCommandService;
    private final ReviewQueryService reviewQueryService;

    /**
     * Constructor for dependency injection
     *
     * @param reviewCommandService Service for handling review commands
     * @param reviewQueryService Service for handling review queries
     */
    public ReviewController(ReviewCommandService reviewCommandService,
                            ReviewQueryService reviewQueryService) {
        this.reviewCommandService = reviewCommandService;
        this.reviewQueryService = reviewQueryService;
    }

    /**
     * Creates a review for an offer
     *
     * @param offerId The offer unique identifier
     * @param resource The review creation request data
     * @return ResponseEntity containing the created review or error status
     */
    @Operation(summary = "Create a review for an offer")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Review created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data or duplicate review"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<ReviewResource> createReview(
            @Parameter(description = "Offer unique identifier") @PathVariable Long offerId,
            @RequestBody CreateReviewResource resource) {
        var command = CreateReviewCommandFromResourceAssembler.toCommandFromResource(offerId, resource);
        var review = reviewCommandService.handle(command);
        return review
                .map(r -> ResponseEntity.status(CREATED)
                        .body(ReviewResourceFromEntityAssembler.toResourceFromEntity(r)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Retrieves all reviews of an offer
     *
     * @param offerId The offer unique identifier
     * @return ResponseEntity containing the list of reviews
     */
    @Operation(summary = "Get all reviews of an offer")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reviews retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<ReviewResource>> getReviewsByOfferId(
            @Parameter(description = "Offer unique identifier") @PathVariable Long offerId) {
        var query = new GetReviewsByOfferIdQuery(offerId);
        var reviews = reviewQueryService.handle(query);
        var resources = reviews.stream()
                .map(ReviewResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}
