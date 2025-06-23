package br.com.challenge_nexdom.backend.configs;

import br.com.challenge_nexdom.backend.services.validators.*;
import br.com.challenge_nexdom.backend.services.validators.implementations.CreateStockMovementValidator;
import br.com.challenge_nexdom.backend.services.validators.implementations.DeleteStockMovementValidator;
import br.com.challenge_nexdom.backend.services.validators.implementations.UpdateStockMovementValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class StrategyConfig {

    @Bean
    public Map<ActionsStock, StockMovementValidationStrategy> stockMovementStrategies(
            CreateStockMovementValidator create,
            UpdateStockMovementValidator update,
            DeleteStockMovementValidator delete
    ) {
        return Map.of(
                ActionsStock.CREATE, create,
                ActionsStock.UPDATE, update,
                ActionsStock.DELETE, delete
        );
    }
}

