package uces.edu.ar.shoppingCart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import uces.edu.ar.shoppingCart.model.Product;

public interface ProductsRepository extends JpaRepository<Product, Long> {

}
