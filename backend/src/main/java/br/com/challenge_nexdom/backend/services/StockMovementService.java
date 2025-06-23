package br.com.challenge_nexdom.backend.services;

import br.com.challenge_nexdom.backend.core.models.movement.MovementType;
import br.com.challenge_nexdom.backend.core.models.movement.StockMovement;
import br.com.challenge_nexdom.backend.core.models.product.Product;
import br.com.challenge_nexdom.backend.core.repositories.ProductRepository;
import br.com.challenge_nexdom.backend.core.repositories.StockMovementRepository;
import br.com.challenge_nexdom.backend.dto.movement.StockMovementDTO;
import br.com.challenge_nexdom.backend.dto.movement.StockMovementRequest;
import br.com.challenge_nexdom.backend.dto.product.ProductProfitDTO;
import br.com.challenge_nexdom.backend.exceptions.ResourceNotFoundException;
import br.com.challenge_nexdom.backend.services.validators.ActionsStock;
import br.com.challenge_nexdom.backend.services.validators.ValidadorMoviments;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockMovementService {
    private final StockMovementRepository stockMovementRepository;
    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;
    private final ValidadorMoviments validate;

    public Page<StockMovementDTO> getAllStockMovements(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return stockMovementRepository.findAll(pageable).map(StockMovementDTO::mapper);
    }

    public List<StockMovementDTO> getRecentStockMovements() {
        Pageable topFive = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "movementDate"));
        return stockMovementRepository.findAll(topFive)
                .getContent()
                .stream()
                .map(StockMovementDTO::mapper)
                .toList();
    }

    public Map<String, Object> findChartDataAsJson (int days) {
        try {
            LocalDateTime endDate = LocalDateTime.now();
            LocalDateTime startDate = endDate.minusDays(days);

            String jsonResult = stockMovementRepository.findChartDataAsJson(startDate, endDate);

            return objectMapper.readValue(jsonResult, new TypeReference<>() {});
        } catch (JsonProcessingException ex) {
            log.error("Erro ao converter JSON do gráfico: {}", ex.getMessage(), ex);
            throw new IllegalStateException("Erro ao interpretar dados do gráfico", ex);
        } catch (Exception ex) {
            log.error("Erro ao buscar dados do gráfico: {}", ex.getMessage(), ex);
            throw new RuntimeException("Erro inesperado ao buscar dados do gráfico", ex);
        }
    }

    public StockMovementDTO getStockMovementById(Long id) {
        return stockMovementRepository.findById(id)
                .map(StockMovementDTO::mapper)
                .orElseThrow(() -> new ResourceNotFoundException("Movimentação com ID " + id + " não encontrada."));
    }

    public StockMovementDTO createStockMovement(StockMovementRequest request) {
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto com ID " + request.productId() + " não encontrado."));

        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        doMovement(movement, request);

        validate.validate(movement, ActionsStock.CREATE);

        StockMovement saved = stockMovementRepository.save(movement);
        return StockMovementDTO.mapper(saved);
    }

    public StockMovementDTO updateStockMovement(Long id, StockMovementRequest request) {
        StockMovement movement = stockMovementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movimentação com ID " + id + " não encontrada."));

        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto com ID " + request.productId() + " não encontrado."));

        movement.setProduct(product);
        doMovement(movement, request);

        validate.validate(movement, ActionsStock.UPDATE);

        StockMovement updated = stockMovementRepository.save(movement);
        return StockMovementDTO.mapper(updated);
    }

    public void deleteStockMovementById(@Positive(message = "O ID deve ser maior que zero.") Long id) {
        StockMovement movement = stockMovementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movimentação com ID " + id + " não encontrada."));

        validate.validate(movement, ActionsStock.DELETE);

        stockMovementRepository.delete(movement);
    }

    private void doMovement(StockMovement movement, StockMovementRequest request) {
        movement.setMovementType(MovementType.valueOf(request.movementType()));
        movement.setSalePrice(request.salePrice());
        movement.setMovementDate(request.movementDate());
        movement.setQuantity(request.quantity());
    }

    public Page<ProductProfitDTO> findProductProfitReport() {
        Pageable pageable = PageRequest.of(0, 1000);
        return stockMovementRepository.findProductProfitReport(pageable);
    }
}
