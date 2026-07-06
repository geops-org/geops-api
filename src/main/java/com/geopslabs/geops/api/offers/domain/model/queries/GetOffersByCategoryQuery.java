package com.geopslabs.geops.api.offers.domain.model.queries;

/**
 * GetOffersByCategoryQuery
 * Query record to retrieve all offers of a category.
 * This query supports the thematic exploration in the map view.
 *
 * @param category The category slug (e.g. "ramen", "k-beauty")
 * @summary Query to retrieve offers by category
 * @since 5.0
 * @author GeOps Labs
 */
public record GetOffersByCategoryQuery(String category) {
    public GetOffersByCategoryQuery {
        if (category == null || category.isBlank())
            throw new IllegalArgumentException("Category cannot be null or blank");
    }
}
