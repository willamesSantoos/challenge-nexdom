package br.com.challenge_nexdom.backend.controllers;

import br.com.challenge_nexdom.backend.core.models.product.Product;
import br.com.challenge_nexdom.backend.core.models.product.ProductType;
import br.com.challenge_nexdom.backend.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        var products = productService.getProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/types")
    public ResponseEntity<List<ProductType>> getProductsTypes() {
        var products = productService.getProductsTypes();
        return ResponseEntity.ok(products);
    }
}
