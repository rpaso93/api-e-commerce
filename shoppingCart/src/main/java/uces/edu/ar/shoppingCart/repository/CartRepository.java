package uces.edu.ar.shoppingCart.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import uces.edu.ar.shoppingCart.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
	Cart findByEmailAndStatus(String email, String status);
	List<Cart> findAllByStatus(String status);
	List<Cart> findAllByStatusOrderByCheckoutDateAsc(String status);
	List<Cart> findAllByStatusAndCheckoutDateBetween(String status, LocalDateTime date, LocalDateTime date2);
	List<Cart> findAllByStatusAndCheckoutDateAfter(String status, LocalDateTime date);
	List<Cart> findAllByStatusAndCheckoutDateBefore(String status, LocalDateTime date);
}
