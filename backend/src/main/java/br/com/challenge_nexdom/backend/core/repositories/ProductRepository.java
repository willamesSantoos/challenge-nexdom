package br.com.challenge_nexdom.backend.core.repositories;

import br.com.challenge_nexdom.backend.core.models.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> { }
