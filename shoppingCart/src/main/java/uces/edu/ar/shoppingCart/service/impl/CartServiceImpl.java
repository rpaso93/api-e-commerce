package uces.edu.ar.shoppingCart.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uces.edu.ar.shoppingCart.exception.CartErrorType;
import uces.edu.ar.shoppingCart.exception.CartException;
import uces.edu.ar.shoppingCart.model.Cart;
import uces.edu.ar.shoppingCart.model.Pedido;
import uces.edu.ar.shoppingCart.model.Product;
import uces.edu.ar.shoppingCart.model.dto.CartDTO;
import uces.edu.ar.shoppingCart.model.dto.CartProductDTO;
import uces.edu.ar.shoppingCart.model.dto.DeletedDTO;
import uces.edu.ar.shoppingCart.model.dto.ProductInCartDTO;
import uces.edu.ar.shoppingCart.model.dto.UserDetailDTO;
import uces.edu.ar.shoppingCart.repository.CartRepository;
import uces.edu.ar.shoppingCart.repository.PedidoRepository;
import uces.edu.ar.shoppingCart.repository.ProductsRepository;
import uces.edu.ar.shoppingCart.service.CartService;
import uces.edu.ar.shoppingCart.utils.CartUtils;

@Service
public class CartServiceImpl implements CartService{
	
	@Autowired
	private CartRepository cartRepo;
	@Autowired
	private ProductsRepository productRepo;
	@Autowired
	private PedidoRepository orderRepo;
		
	@Override
	public List<CartDTO> getAll() {
		
		List<Cart> carts = cartRepo.findAll();
		List<CartDTO> cartsDTO = CartUtils.cartsToCartsDTO(carts);
		
		return cartsDTO;
	}

	@Override
	public CartDTO getById(Long id) {
		
		Optional<Cart> cart = findCartById(id);
		CartDTO cartDTO = new CartDTO();
		
		CartUtils.isCartPresent(cart);
		cartDTO = CartUtils.cartToDTO(cart.get());
				
		return cartDTO;
	}

	@Override
	public List<ProductInCartDTO> getProductsById(Long id) {
		Optional<Cart> cart = findCartById(id);
		List<ProductInCartDTO> productos = new ArrayList<>();
		
		CartUtils.isCartPresent(cart);
		for (Pedido pedido : cart.get().getPedidos()) {
			ProductInCartDTO cartP = CartUtils.PedidoToProductInCartDTO(pedido);
			productos.add(cartP);
		}
		
		return productos;
	}

	@Override
	public CartDTO postCart(UserDetailDTO user) {
		CartUtils.areEmailAndFullnameValid(user);		
		Cart existingCart = cartRepo.findByEmailAndStatus(user.getEmail(), "NEW");
		CartDTO cartDTO;
		
		if (existingCart != null) {
			cartDTO = CartUtils.cartToDTO(existingCart);
			return cartDTO;
		}
		Cart cart = CartUtils.initCart(user);
		cart = cartRepo.save(cart);
		cartDTO = CartUtils.cartToDTO(cart);
		
		return cartDTO;
	}

	@Override
	public CartDTO postProducts(Long id, CartProductDTO productDto) {

		Optional<Cart> cart = findCartById(id);
		Optional<Product> product = findProductById(productDto.getId());
		
		CartUtils.validateToAddProduct(productDto, cart, product);
		
		cart = addOrUpdatePedido(productDto, cart, product);
		cartRepo.save(cart.get());
		CartDTO cartDto = CartUtils.cartToDTO(cart.get());
		
		return cartDto;
	}		

	@Override
	public CartDTO postCheckout(Long id) {
		Optional<Cart> cart = findCartById(id);
		CartUtils.validateCartToCheckout(cart);

		cart.get().setStatus("READY");
		cart.get().setCheckoutDate(LocalDateTime.now());
		cartRepo.save(cart.get());
		CartDTO cartDto = CartUtils.cartToDTO(cart.get());
		return cartDto;
	}

	@Override
	public DeletedDTO deleteById(Long id, Long productId) {
		Optional<Cart> cart = findCartById(id);
		Optional<Product> product = findProductById(productId);

		CartUtils.validateToDeleteProduct(cart, product);
		updateCartAfterDelete(cart, product);
		
		return new DeletedDTO(productId);
	}

	@Override
	public List<CartDTO> getCartsByStatus(Optional<String> status) {
		List<CartDTO> cartsDTO;
		if(status.isPresent()) {
			CartUtils.isStatusValid(status.get());
			List<Cart> carts = cartRepo.findAllByStatus(status.get());
			cartsDTO = CartUtils.cartsToCartsDTO(carts);
		}else {
			cartsDTO = this.getAll();
		}
		return cartsDTO;
	}
		
	private Optional<Product> findProductById(Long id) {
		return productRepo.findById(id);
	}

	private Optional<Cart> findCartById(Long id) {
		return cartRepo.findById(id);
	}
	
	private Optional<Pedido> findPedidoInCart(Optional<Cart> cart, Optional<Product> product) {
		return orderRepo.findByCartAndProduct(cart.get(), product.get());
	}
	
	private Optional<Cart> addOrUpdatePedido(CartProductDTO productDto, Optional<Cart> cart, Optional<Product> product) {
		Optional<Pedido> existingPedido = findPedidoInCart(cart, product);
		BigDecimal amount = null;
		
		if(existingPedido.isPresent()) {
			updateQuantity(productDto, existingPedido);
			amount = CartUtils.calculateAmountForExistingPedido(productDto, existingPedido);
		}else {
			Pedido pedido = CartUtils.initPedido(cart.get(), product.get(), productDto);
			pedido = orderRepo.save(pedido);
			cart.get().addPedido(pedido);
			amount = CartUtils.calculateAmountForPedido(pedido);
		}
		cart.get().addToTotal(amount);
		return cart;
	}
	
	private void updateQuantity(CartProductDTO productDto, Optional<Pedido> existingPedido) {
		existingPedido.get().addToQuantity(productDto.getQuantity());
		orderRepo.save(existingPedido.get());
	}
	
	private void updateCartAfterDelete(Optional<Cart> cart, Optional<Product> product) {
		Optional<Pedido> pedidoForDelete = findPedidoInCart(cart, product);
		if(pedidoForDelete.isPresent()) {
			BigDecimal amount = CartUtils.calculateAmountForPedido(pedidoForDelete.get());
			cart.get().subtractToTotal(amount);
			orderRepo.delete(pedidoForDelete.get());
			cart.get().getPedidos().remove(pedidoForDelete.get());
			cartRepo.save(cart.get());
		}else {
			throw new CartException(CartErrorType.PRODUCT_NOT_PRESENT_IN_CART);
		}
	}
}
