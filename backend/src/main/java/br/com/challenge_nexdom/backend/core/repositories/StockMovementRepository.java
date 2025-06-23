package br.com.challenge_nexdom.backend.core.repositories;

import br.com.challenge_nexdom.backend.core.models.movement.StockMovement;
import br.com.challenge_nexdom.backend.dto.product.ProductProfitDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    @EntityGraph(attributePaths = {"product"})
    Page<StockMovement> findAll(Pageable pageable);

    @Query(value = """
    WITH date_range AS (
        SELECT 
            FORMATDATETIME(DATEADD('DAY', i, CAST(:startDate AS DATE)), 'dd/MM') AS dia,
            DATEADD('DAY', i, CAST(:startDate AS DATE)) AS data_real
        FROM SYSTEM_RANGE(0, DATEDIFF('DAY', :startDate, :endDate)) AS range(i)
    ),
    entradas AS (
        SELECT FORMATDATETIME(movement_date, 'dd/MM') AS dia, SUM(quantity) AS total
        FROM stock_movements
        WHERE movement_type = 'IN'
        AND movement_date BETWEEN :startDate AND :endDate
        GROUP BY FORMATDATETIME(movement_date, 'dd/MM')
    ),
    saidas AS (
        SELECT FORMATDATETIME(movement_date, 'dd/MM') AS dia, SUM(quantity) AS total
        FROM stock_movements
        WHERE movement_type = 'OUT'
        AND movement_date BETWEEN :startDate AND :endDate
        GROUP BY FORMATDATETIME(movement_date, 'dd/MM')
    )
    SELECT JSON_OBJECT(
        'labels' VALUE JSON_ARRAYAGG(r.dia ORDER BY r.data_real),
        'datasets' VALUE JSON_ARRAY(
            JSON_OBJECT(
                'label' VALUE 'Entradas',
                'data' VALUE JSON_ARRAYAGG(COALESCE(e.total, 0) ORDER BY r.data_real),
                'backgroundColor' VALUE '#10b981',
                'borderRadius' VALUE 5
            ),
            JSON_OBJECT(
                'label' VALUE 'Saídas',
                'data' VALUE JSON_ARRAYAGG(COALESCE(s.total, 0) ORDER BY r.data_real),
                'backgroundColor' VALUE '#3b82f6',
                'borderRadius' VALUE 5
            )
        )
    ) AS chart_data
    FROM date_range r
    LEFT JOIN entradas e ON r.dia = e.dia
    LEFT JOIN saidas s ON r.dia = s.dia
    """, nativeQuery = true)
    String findChartDataAsJson(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    boolean existsByProductId(Long productId);

    @Query("""
        SELECT new br.com.challenge_nexdom.backend.dto.product.ProductProfitDTO(
            m.product.description,
            SUM(m.quantity),
            SUM(m.salePrice * m.quantity),
            SUM(m.product.supplierPrice * m.quantity),
            SUM((m.salePrice - m.product.supplierPrice) * m.quantity)
        )
        FROM StockMovement m
        WHERE m.movementType = 'OUT'
        GROUP BY m.product.id, m.product.description
    """)
    Page<ProductProfitDTO> findProductProfitReport(Pageable pageable);

}
