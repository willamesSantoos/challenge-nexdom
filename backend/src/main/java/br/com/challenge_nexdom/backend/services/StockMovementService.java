package br.com.challenge_nexdom.backend.services;

import br.com.challenge_nexdom.backend.core.repositories.StockMovementRepository;
import br.com.challenge_nexdom.backend.dto.StockMovementDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockMovementService {
    private final StockMovementRepository stockMovementRepository;
    private final ObjectMapper objectMapper;

    public List<StockMovementDTO> getAllStockMovements(){
        return stockMovementRepository.findAll().stream()
                .map(StockMovementDTO::mapper).toList();
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
}
