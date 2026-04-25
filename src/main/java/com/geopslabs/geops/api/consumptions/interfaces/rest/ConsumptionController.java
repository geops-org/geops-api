package com.geopslabs.geops.api.consumptions.interfaces.rest;

import com.geopslabs.geops.api.consumptions.domain.model.queries.GetConsumptionsByUserQuery;
import com.geopslabs.geops.api.consumptions.domain.services.ConsumptionCommandService;
import com.geopslabs.geops.api.consumptions.domain.services.ConsumptionQueryService;
import com.geopslabs.geops.api.consumptions.interfaces.rest.resources.ConsumptionResource;
import com.geopslabs.geops.api.consumptions.interfaces.rest.resources.RegisterVisitResource;
import com.geopslabs.geops.api.consumptions.interfaces.rest.transform.ConsumptionResourceFromEntityAssembler;
import com.geopslabs.geops.api.consumptions.interfaces.rest.transform.RegisterVisitCommandFromResourceAssembler;
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

@Tag(name = "Consumptions", description = "Offer visit tracking operations")
@RestController
@RequestMapping(value = "/api/v1/consumptions", produces = APPLICATION_JSON_VALUE)
public class ConsumptionController {

    private final ConsumptionCommandService consumptionCommandService;
    private final ConsumptionQueryService consumptionQueryService;

    public ConsumptionController(ConsumptionCommandService consumptionCommandService,
                                 ConsumptionQueryService consumptionQueryService) {
        this.consumptionCommandService = consumptionCommandService;
        this.consumptionQueryService = consumptionQueryService;
    }

    @Operation(summary = "Register offer visit")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Visit registered successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping
    public ResponseEntity<ConsumptionResource> register(@RequestBody RegisterVisitResource resource) {
        var command = RegisterVisitCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = consumptionCommandService.handle(command);

        if (result.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return new ResponseEntity<>(
            ConsumptionResourceFromEntityAssembler.toResourceFromEntity(result.get()),
            CREATED
        );
    }

    @Operation(summary = "Get visits by user ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Visits retrieved successfully")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ConsumptionResource>> getByUser(
            @Parameter(description = "User unique identifier") @PathVariable Long userId) {

        var query = new GetConsumptionsByUserQuery(userId);
        var consumptions = consumptionQueryService.handle(query);

        var resources = consumptions.stream()
                .map(ConsumptionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }
}
