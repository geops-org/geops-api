package com.geopslabs.geops.api.cart.interfaces.rest.resources;

/**
 * Resource for cart item in REST responses
 */
public record CartItemResource(
    Long id,
    Long userId,
    Long offerId,
    String offerTitle,
    Double offerPrice,
    String offerImageUrl,
    Integer quantity,
    Double total
) {
}

