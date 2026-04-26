package com.geopslabs.geops.api.reviews.interfaces.rest;

import com.geopslabs.geops.api.reviews.domain.model.exceptions.ReviewAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = ReviewController.class)
public class ReviewExceptionHandler {

    record ReviewErrorResponse(String code, String message) {}

    @ExceptionHandler(ReviewAlreadyExistsException.class)
    public ResponseEntity<ReviewErrorResponse> handleAlreadyExists(ReviewAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ReviewErrorResponse("REVIEW_ALREADY_EXISTS", ex.getMessage()));
    }
}
