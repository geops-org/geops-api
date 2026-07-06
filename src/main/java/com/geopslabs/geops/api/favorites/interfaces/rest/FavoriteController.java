package com.geopslabs.geops.api.favorites.interfaces.rest;

import com.geopslabs.geops.api.favorites.domain.model.commands.DeleteFavoriteCommand;
import com.geopslabs.geops.api.favorites.domain.model.queries.GetFavoritesByUserIdQuery;
import com.geopslabs.geops.api.favorites.domain.services.FavoriteCommandService;
import com.geopslabs.geops.api.favorites.domain.services.FavoriteQueryService;
import com.geopslabs.geops.api.favorites.interfaces.rest.resources.CreateFavoriteResource;
import com.geopslabs.geops.api.favorites.interfaces.rest.resources.FavoriteResource;
import com.geopslabs.geops.api.favorites.interfaces.rest.transform.CreateFavoriteCommandFromResourceAssembler;
import com.geopslabs.geops.api.favorites.interfaces.rest.transform.FavoriteResourceFromEntityAssembler;
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
 * FavoriteController
 * REST controller that exposes favorite endpoints for the GeOps platform.
 * This controller handles HTTP requests to save, list and remove favorite offers
 * for a given user.
 *
 * @summary REST controller for favorite operations
 * @since 5.0
 * @author GeOps Labs
 */
@Tag(name = "Favorites", description = "Favorite offers operations and management")
@RestController
@RequestMapping(value = "/api/v1/users/{userId}/favorites", produces = APPLICATION_JSON_VALUE)
public class FavoriteController {

    private final FavoriteCommandService favoriteCommandService;
    private final FavoriteQueryService favoriteQueryService;

    /**
     * Constructor for dependency injection
     *
     * @param favoriteCommandService Service for handling favorite commands
     * @param favoriteQueryService Service for handling favorite queries
     */
    public FavoriteController(FavoriteCommandService favoriteCommandService,
                              FavoriteQueryService favoriteQueryService) {
        this.favoriteCommandService = favoriteCommandService;
        this.favoriteQueryService = favoriteQueryService;
    }

    /**
     * Saves an offer as favorite for a user
     *
     * @param userId The user unique identifier
     * @param resource The favorite creation request data
     * @return ResponseEntity containing the created favorite or error status
     */
    @Operation(summary = "Save an offer as favorite")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Favorite created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data or duplicate favorite"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<FavoriteResource> createFavorite(
            @Parameter(description = "User unique identifier") @PathVariable Long userId,
            @RequestBody CreateFavoriteResource resource) {
        var command = CreateFavoriteCommandFromResourceAssembler.toCommandFromResource(userId, resource);
        var favorite = favoriteCommandService.handle(command);
        return favorite
                .map(f -> ResponseEntity.status(CREATED)
                        .body(FavoriteResourceFromEntityAssembler.toResourceFromEntity(f)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Retrieves all favorites saved by a user
     *
     * @param userId The user unique identifier
     * @return ResponseEntity containing the list of favorites
     */
    @Operation(summary = "Get all favorites of a user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Favorites retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<FavoriteResource>> getFavoritesByUserId(
            @Parameter(description = "User unique identifier") @PathVariable Long userId) {
        var query = new GetFavoritesByUserIdQuery(userId);
        var favorites = favoriteQueryService.handle(query);
        var resources = favorites.stream()
                .map(FavoriteResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    /**
     * Removes an offer from a user's favorites
     *
     * @param userId The user unique identifier
     * @param offerId The offer unique identifier
     * @return ResponseEntity with no content if removed, not found otherwise
     */
    @Operation(summary = "Remove an offer from favorites")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Favorite removed successfully"),
        @ApiResponse(responseCode = "404", description = "Favorite not found")
    })
    @DeleteMapping("/{offerId}")
    public ResponseEntity<Void> deleteFavorite(
            @Parameter(description = "User unique identifier") @PathVariable Long userId,
            @Parameter(description = "Offer unique identifier") @PathVariable Long offerId) {
        var command = new DeleteFavoriteCommand(userId, offerId);
        var removed = favoriteCommandService.handle(command);
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
