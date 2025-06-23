package br.com.challenge_nexdom.backend.controllers;

import br.com.challenge_nexdom.backend.dto.movement.StockMovementDTO;
import br.com.challenge_nexdom.backend.dto.movement.StockMovementRequest;
import br.com.challenge_nexdom.backend.dto.product.ProductProfitDTO;
import br.com.challenge_nexdom.backend.services.StockMovementService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/movements")
@RequiredArgsConstructor
public class StockMovementController {
    private final StockMovementService stockMovementService;

    @GetMapping
    public ResponseEntity<Page<StockMovementDTO>> getAllStockMovements(
            @RequestParam(defaultValue = "0") @Min(value = 0, message = "A página não pode ser negativa.") int page,
            @RequestParam(defaultValue = "10") @Positive(message = "O tamanho deve ser maior que zero.") int size
    ) {
        var movements = stockMovementService.getAllStockMovements(page, size);
        return ResponseEntity.ok(movements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockMovementDTO> getProductById(
            @PathVariable("id") @Positive(message = "O ID deve ser maior que zero.") Long id
    ) {
        var movement = stockMovementService.getStockMovementById(id);
        return ResponseEntity.ok(movement);
    }

    @PostMapping("/new")
    public ResponseEntity<StockMovementDTO> newProduct(
            @Valid @RequestBody StockMovementRequest request
    ) {
        var movement = stockMovementService.createStockMovement(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(movement);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<StockMovementDTO> editProduct(
            @PathVariable("id") @Positive(message = "O ID deve ser maior que zero.") Long id,
            @Valid @RequestBody StockMovementRequest request
    ) {
        var movement = stockMovementService.updateStockMovement(id, request);
        return ResponseEntity.ok(movement);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable("id") @Positive(message = "O ID deve ser maior que zero.") Long id
    ) {
        stockMovementService.deleteStockMovementById(id);
        return ResponseEntity.noContent().build();
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

    @GetMapping("/profit-by-product")
    public ResponseEntity<Page<ProductProfitDTO>> findProductProfitReport() {
        return ResponseEntity.ok(stockMovementService.findProductProfitReport());
    }
}
