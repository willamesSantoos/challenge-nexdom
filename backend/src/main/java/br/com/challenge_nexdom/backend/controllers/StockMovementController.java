package br.com.challenge_nexdom.backend.controllers;

import br.com.challenge_nexdom.backend.dto.StockMovementDTO;
import br.com.challenge_nexdom.backend.services.StockMovementService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/movements")
@RequiredArgsConstructor
public class StockMovementController {
    private final StockMovementService stockMovementService;

    @GetMapping
    public ResponseEntity<List<StockMovementDTO>> getAllStockMovements() {
        List<StockMovementDTO> movements = stockMovementService.getAllStockMovements();
        return ResponseEntity.ok(movements);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<StockMovementDTO>> getRecentStockMovements() {
        List<StockMovementDTO> movements = stockMovementService.getRecentStockMovements();
        return ResponseEntity.ok(movements);
    }

    @GetMapping("/chart/{days}")
    public ResponseEntity<Map<String, Object>> getMovementChartData(@PathVariable("days") int days) {
        Map<String, Object> response = stockMovementService.findChartDataAsJson(days);
        return ResponseEntity.ok(response);
    }
}
