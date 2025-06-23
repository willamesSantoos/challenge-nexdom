package br.com.challenge_nexdom.backend.core.repositories;

import br.com.challenge_nexdom.backend.core.models.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> { }
