package com.geopslabs.geops.api.cart.interfaces.rest.resources;

import java.util.List;

/**
 * Resource for cart responses
 */
public record CartResource(
    Long id,
    Long userId,
    List<CartItemResource> items,
    Integer totalItems,
    Double totalAmount,
    String createdAt,
    String updatedAt
) {
}

