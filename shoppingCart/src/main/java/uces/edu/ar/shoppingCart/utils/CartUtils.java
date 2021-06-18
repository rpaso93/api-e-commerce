package uces.edu.ar.shoppingCart.utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.micrometer.core.instrument.util.StringUtils;
import uces.edu.ar.shoppingCart.exception.CartErrorType;
import uces.edu.ar.shoppingCart.exception.CartException;
import uces.edu.ar.shoppingCart.exception.ProductErrorType;
import uces.edu.ar.shoppingCart.exception.ProductException;
import uces.edu.ar.shoppingCart.model.Cart;
import uces.edu.ar.shoppingCart.model.Pedido;
import uces.edu.ar.shoppingCart.model.Product;
import uces.edu.ar.shoppingCart.model.dto.CartDTO;
import uces.edu.ar.shoppingCart.model.dto.CartProductDTO;
import uces.edu.ar.shoppingCart.model.dto.ProductInCartDTO;
import uces.edu.ar.shoppingCart.model.dto.UserDetailDTO;

public class CartUtils {
	
	public static Cart initCart(UserDetailDTO user) {
		Cart cart = new Cart();
		
		cart.setFullName(user.getFullName());
		cart.setEmail(user.getEmail());
		cart.setCreationDate(LocalDate.now());
		cart.setTotal(new BigDecimal(0));
		cart.setStatus("NEW");
		
		return cart;
	}
	
	public static CartDTO cartToDTO(Cart cart) {
		CartDTO cartDto = new CartDTO();
		cartDto.setId(cart.getId());
		cartDto.setFullName(cart.getFullName());
		cartDto.setEmail(cart.getEmail());
		cartDto.setCreationDate(cart.getCreationDate());
		if(cart.getCheckoutDate() != null) { cartDto.setCheckoutDate(cart.getCheckoutDate()); }
		cartDto.setStatus(cart.getStatus());
		cartDto.setTotal(cart.getTotal());
		
		for(Pedido pedido : cart.getPedidos()) {				
			ProductInCartDTO cartP = new ProductInCartDTO();
			cartP.setId(pedido.getProduct().getId());
			cartP.setQuantity(pedido.getQuantity());
			cartP.setDescription(pedido.getProduct().getDescription());
			cartP.setUnitPrice(pedido.getUnitPrice());
			cartDto.setProducts(cartP);
		}
		
		return cartDto;
	}
	
	public static List<CartDTO> cartsToCartsDTO(List<Cart> carts){
		List<CartDTO> cartsDTO = new ArrayList<>();
		for(Cart cart : carts) {
			CartDTO cartDTO = CartUtils.cartToDTO(cart);
			cartsDTO.add(cartDTO);
		}
		return cartsDTO;
	}
	
	public static Pedido initPedido(Cart cart, Product product, CartProductDTO dto) {
		Pedido pedido = new Pedido();
		pedido.setCart(cart);
		pedido.setProduct(product);
		pedido.setUnitPrice(product.getUnitPrice());
		pedido.setQuantity(dto.getQuantity());
		
		return pedido;
	}
	
	public static ProductInCartDTO productToPICDTO(Product product, CartProductDTO dto) {
		ProductInCartDTO pDto = new ProductInCartDTO();
		pDto.setId(product.getId());
		pDto.setDescription(product.getDescription());
		pDto.setUnitPrice(product.getUnitPrice());
		pDto.setQuantity(dto.getQuantity());
		
		return pDto;
	}
	
	public static ProductInCartDTO PedidoToProductInCartDTO(Pedido pedido) {
		ProductInCartDTO pDto = new ProductInCartDTO();
		pDto.setId(pedido.getProduct().getId());
		pDto.setQuantity(pedido.getQuantity());
		pDto.setDescription(pedido.getProduct().getDescription());
		pDto.setUnitPrice(pedido.getUnitPrice());
		
		return pDto;
	}
	
	public static BigDecimal calculateAmountForPedido(Pedido pedido) {
		return pedido.getUnitPrice().multiply(BigDecimal.valueOf(pedido.getQuantity()));
	}

	public static BigDecimal calculateAmountForExistingPedido(CartProductDTO productDto, Optional<Pedido> existingPedido) {
		return existingPedido.get().getUnitPrice().multiply(BigDecimal.valueOf(productDto.getQuantity()));
	}
	
	public static void isCartPresent(Optional<Cart> cart) {
		if(!cart.isPresent()) {
			throw new CartException(CartErrorType.CART_NOT_PRESENT);
		}
	}
	
	public static void isProductPresent(Optional<Product> product) {
		if(!product.isPresent()) {
			throw new ProductException(ProductErrorType.PRODUCT_NOT_PRESENT);
		}
	}
	
	public static void isStatusValid(String status) {
		if(!(status.equals("NEW") || status.equals("READY") || status.equals("PROCESSED") || status.equals("FAILED"))) {
			throw new CartException(CartErrorType.CART_STATUS_NOT_SUPPORTED);
		}
	}
	
	public static void validateToAddProduct(CartProductDTO productDto, Optional<Cart> cart, Optional<Product> product) {
		isCartPresent(cart);
		if (cart.get().getStatus().equals("NEW")) {
			isProductPresent(product);
			if (productDto.getQuantity() != null) {
				if (productDto.getQuantity() > 0) {
					if (productDto.getQuantity() <= product.get().getStock()) {

					} else {
						throw new CartException(CartErrorType.PRODUCT_STOCK_INSUFFICIENT);
					}
				} else {
					throw new CartException(CartErrorType.PRODUCT_QUANTITY_INVALID);
				}
			} else {
				throw new CartException(CartErrorType.PRODUCT_QUANTITY_REQUIRED);
			}
		} else {
			throw new CartException(CartErrorType.CART_STATUS_NOT_ALLOW_CHECKOUT);
		}
	}

	public static void validateToDeleteProduct(Optional<Cart> cart, Optional<Product> product) {
		isCartPresent(cart);
		if (cart.get().getStatus().equals("NEW")) {
			isProductPresent(product);
		} else {
			throw new CartException(CartErrorType.CART_PROCESSING_NOT_ALLOW_DELETION);
		}	
	}
	
	public static void validateCartToCheckout(Optional<Cart> cart) {
		isCartPresent(cart);
		if(!(cart.get().getStatus().equals("NEW") && !cart.get().getPedidos().isEmpty())) {
			throw new CartException(CartErrorType.CART_STATUS_NOT_ALLOW_CHECKOUT);
		}
	}
		
	public static void areEmailAndFullnameValid(UserDetailDTO user) {
		if(StringUtils.isEmpty(user.getEmail())) {
			throw new CartException(CartErrorType.CART_EMAIL_REQUIRED);
		}
		if(StringUtils.isEmpty(user.getFullName())) {
			throw new CartException(CartErrorType.CART_FULLNAME_REQUIRED);
		}
	}
}
