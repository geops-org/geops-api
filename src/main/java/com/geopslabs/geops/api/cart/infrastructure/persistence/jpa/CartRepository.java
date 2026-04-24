package com.geopslabs.geops.api.cart.infrastructure.persistence.jpa;

import com.geopslabs.geops.api.cart.domain.model.aggregates.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA repository for Cart aggregate
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUserId(Long userId);
}

