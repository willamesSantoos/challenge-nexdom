package br.com.challenge_nexdom.backend.dto.product;

import br.com.challenge_nexdom.backend.core.models.product.ProductType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Data
@Builder
public class ProductFilterRequest {

    @Builder.Default
    @Min(value = 0, message = "A página não pode ser negativa.")
    private int page = 0;

    @Builder.Default
    @Positive(message = "O tamanho deve ser maior que zero.")
    private int size = 10;

    private String description;

    private ProductType productType;

    private Integer stockQuantity;

    public Pageable toPageable() {
        return PageRequest.of(page, size);
    }
}

