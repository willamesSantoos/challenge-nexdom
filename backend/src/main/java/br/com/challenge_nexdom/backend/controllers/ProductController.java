package br.com.challenge_nexdom.backend.controllers;

import br.com.challenge_nexdom.backend.core.models.product.ProductType;
import br.com.challenge_nexdom.backend.dto.product.ProductDTO;
import br.com.challenge_nexdom.backend.dto.product.ProductFilterRequest;
import br.com.challenge_nexdom.backend.dto.product.ProductRequest;
import br.com.challenge_nexdom.backend.services.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Validated
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getProducts(
            @RequestParam(defaultValue = "0") @Min(value = 0, message = "A página não pode ser negativa.") int page,
            @RequestParam(defaultValue = "10") @Positive(message = "O tamanho deve ser maior que zero.") int size,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) ProductType productType,
            @RequestParam(required = false) Integer stockQuantity
    ) {

        ProductFilterRequest filter = ProductFilterRequest.builder()
            .page(page)
            .size(size)
            .description(description)
            .productType(productType)
            .stockQuantity(stockQuantity)
            .build();

        var products = productService.getProducts(filter);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(
            @PathVariable("id") @Positive(message = "O ID deve ser maior que zero.") Long id
    ) {
        var product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/new")
    public ResponseEntity<ProductDTO> newProduct(
            @Valid @RequestBody ProductRequest request
    ) {
        var product = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ProductDTO> editProduct(
            @PathVariable("id") @Positive(message = "O ID deve ser maior que zero.") Long id,
            @Valid @RequestBody ProductRequest request
    ) {
        var product = productService.updateProduct(id, request);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable("id") @Positive(message = "O ID deve ser maior que zero.") Long id
    ) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/types")
    public ResponseEntity<List<ProductType>> getProductsTypes() {
        var products = productService.getProductsTypes();
        return ResponseEntity.ok(products);
    }
}
