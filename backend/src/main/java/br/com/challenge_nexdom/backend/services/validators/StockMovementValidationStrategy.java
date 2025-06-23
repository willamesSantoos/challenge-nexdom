package br.com.challenge_nexdom.backend.services.validators;

import br.com.challenge_nexdom.backend.core.models.movement.StockMovement;

public interface StockMovementValidationStrategy {
    void validate(StockMovement movement);
}

