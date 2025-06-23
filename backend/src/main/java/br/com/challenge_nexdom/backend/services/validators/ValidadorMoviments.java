package br.com.challenge_nexdom.backend.services.validators;

import br.com.challenge_nexdom.backend.core.models.movement.StockMovement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ValidadorMoviments {

    private final Map<ActionsStock, StockMovementValidationStrategy> strategyMap;

    public void validate(StockMovement movement, ActionsStock action) {
        StockMovementValidationStrategy strategy = strategyMap.get(action);

        if (strategy == null)
            throw new IllegalArgumentException("Nenhuma estratégia de validação encontrada para a ação: " + action);

        strategy.validate(movement);
    }
}

