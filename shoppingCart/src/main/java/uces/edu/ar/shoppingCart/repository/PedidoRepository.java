package uces.edu.ar.shoppingCart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import uces.edu.ar.shoppingCart.model.Cart;
import uces.edu.ar.shoppingCart.model.Pedido;
import uces.edu.ar.shoppingCart.model.Product;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{
	Optional<Pedido> findByCartAndProduct(Cart cart, Product product);
}
