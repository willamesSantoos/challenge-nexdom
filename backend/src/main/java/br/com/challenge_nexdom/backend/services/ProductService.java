package br.com.challenge_nexdom.backend.services;

import br.com.challenge_nexdom.backend.controllers.specifications.ProductSpecification;
import br.com.challenge_nexdom.backend.core.models.product.Product;
import br.com.challenge_nexdom.backend.core.models.product.ProductType;
import br.com.challenge_nexdom.backend.core.repositories.ProductRepository;
import br.com.challenge_nexdom.backend.core.repositories.StockMovementRepository;
import br.com.challenge_nexdom.backend.dto.product.ProductDTO;
import br.com.challenge_nexdom.backend.dto.product.ProductFilterRequest;
import br.com.challenge_nexdom.backend.dto.product.ProductRequest;
import br.com.challenge_nexdom.backend.exceptions.ProductHasMovementsException;
import br.com.challenge_nexdom.backend.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final StockMovementRepository stockMovementRepository;

    public Page<ProductDTO> getProducts(ProductFilterRequest filter) {
        Specification<Product> spec = ProductSpecification.withFilters(filter.getDescription(), filter.getProductType(), filter.getStockQuantity());

        Pageable pageable = filter.toPageable();
        return productRepository.findAll(spec, pageable).map(ProductDTO::mapper);
    }

    public ProductDTO createProduct(ProductRequest request) {
        Product product = new Product();
        doProduct(product, request);

        product = productRepository.save(product);
        return ProductDTO.mapper(product);
    }

    public ProductDTO updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto com ID " + id + " não foi encontrado."));

        doProduct(product, request);

        product = productRepository.save(product);
        return ProductDTO.mapper(product);
    }

    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id)
                .map(ProductDTO::mapper)
                .orElseThrow(() -> new ResourceNotFoundException("Produto com ID " + id + " não foi encontrado."));
    }

    public void deleteProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto com ID " + id + " não foi encontrado."));

        if (stockMovementRepository.existsByProductId(id)) {
            throw new ProductHasMovementsException(product.getCode());
        }

        productRepository.deleteById(id);
    }

    public List<ProductType> getProductsTypes(){
        var types = Arrays.stream(ProductType.values()).toList();
        return types;
    }

    private void doProduct (Product product, ProductRequest request) {
        product.setCode(request.code());
        product.setDescription(request.description());
        product.setProductType(ProductType.valueOf(request.productType().toUpperCase()));
        product.setSupplierPrice(request.supplierPrice());
        product.setStockQuantity(request.stockQuantity());
    }
}
