package uces.edu.ar.shoppingCart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import uces.edu.ar.shoppingCart.model.dto.CartDTO;
import uces.edu.ar.shoppingCart.model.dto.CartProductDTO;
import uces.edu.ar.shoppingCart.model.dto.DeletedDTO;
import uces.edu.ar.shoppingCart.model.dto.ProductInCartDTO;
import uces.edu.ar.shoppingCart.model.dto.UserDetailDTO;

@Service
public interface CartService {

	List<CartDTO> getAll();
	CartDTO getById(Long id);
	List<ProductInCartDTO> getProductsById(Long id);
	CartDTO postCart(UserDetailDTO user);
	CartDTO postProducts(Long id, CartProductDTO product);
	CartDTO postCheckout(Long id);
	DeletedDTO deleteById(Long id, Long productId);
	List<CartDTO> getCartsByStatus(Optional<String> status);
}
