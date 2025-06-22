package br.com.challenge_nexdom.backend.services;

import br.com.challenge_nexdom.backend.core.models.product.Product;
import br.com.challenge_nexdom.backend.core.models.product.ProductType;
import br.com.challenge_nexdom.backend.core.repositories.ProductRepository;
import br.com.challenge_nexdom.backend.core.repositories.StockMovementRepository;
import br.com.challenge_nexdom.backend.dto.ProductDTO;
import br.com.challenge_nexdom.backend.dto.ProductResponse;
import br.com.challenge_nexdom.backend.exceptions.ProductHasMovementsException;
import br.com.challenge_nexdom.backend.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final StockMovementRepository stockMovementRepository;

    public Page<ProductDTO> getProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable).map(ProductDTO::mapper);
    }

    public ProductDTO createProduct(ProductResponse response) {
        Product product = new Product();
        product.setCode(response.code());
        product.setDescription(response.description());
        product.setProductType(ProductType.valueOf(response.productType().toUpperCase()));
        product.setSupplierPrice(response.supplierPrice());
        product.setStockQuantity(response.stockQuantity());

        product = productRepository.save(product);
        return ProductDTO.mapper(product);
    }

    public ProductDTO updateProduct(Long id, ProductResponse response) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto com ID " + id + " não foi encontrado."));

        product.setCode(response.code());
        product.setDescription(response.description());
        product.setProductType(ProductType.valueOf(response.productType().toUpperCase()));
        product.setSupplierPrice(response.supplierPrice());
        product.setStockQuantity(response.stockQuantity());

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
}
