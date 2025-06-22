package br.com.challenge_nexdom.backend.services;

import br.com.challenge_nexdom.backend.core.models.product.Product;
import br.com.challenge_nexdom.backend.core.models.product.ProductType;
import br.com.challenge_nexdom.backend.core.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    public List<ProductType> getProductsTypes(){
        var types = Arrays.stream(ProductType.values()).toList();
        return types;
    }
}
