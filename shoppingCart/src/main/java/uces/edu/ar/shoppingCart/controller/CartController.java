package uces.edu.ar.shoppingCart.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import uces.edu.ar.shoppingCart.model.dto.CartDTO;
import uces.edu.ar.shoppingCart.model.dto.CartProductDTO;
import uces.edu.ar.shoppingCart.model.dto.DeletedDTO;
import uces.edu.ar.shoppingCart.model.dto.ProductInCartDTO;
import uces.edu.ar.shoppingCart.model.dto.UserDetailDTO;
import uces.edu.ar.shoppingCart.service.CartService;

@RestController
@Validated
public class CartController {
	@Autowired
	private CartService service;
	
	@GetMapping(value = "/carts")
	public ResponseEntity<List<CartDTO>> getCarts(){
		return new ResponseEntity<>(service.getAll(),HttpStatus.OK);
	}
	
	@GetMapping(value = "/carts/{id}")
	public ResponseEntity<CartDTO> getCart(@PathVariable long id){
		return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/carts/{id}/products")
	public ResponseEntity<List<ProductInCartDTO>> getProductsFromCart(@PathVariable long id){
		return new ResponseEntity<>(service.getProductsById(id), HttpStatus.OK);
	}
	
	@GetMapping(value = {"/carts/status/{status}","/carts/status"})
	public ResponseEntity<List<CartDTO>> getCartsByStatus(@PathVariable(required=false) Optional<String> status){
		return new ResponseEntity<>(service.getCartsByStatus(status), HttpStatus.OK);
	}
	
	@PostMapping(path = "/carts")
	public ResponseEntity<CartDTO> postCart(@Valid @RequestBody UserDetailDTO userDetail){
		return new ResponseEntity<>(service.postCart(userDetail), HttpStatus.CREATED);
	}
	
	@PostMapping(path = "/carts/{id}/products")
	public ResponseEntity<CartDTO> postProductsToCart(@PathVariable Long id,@Valid @RequestBody CartProductDTO product){
		return new ResponseEntity<>(service.postProducts(id, product), HttpStatus.OK);
	}
	
	@PostMapping(path = "/carts/{id}/checkout")
	public ResponseEntity<CartDTO> postCheckout(@PathVariable Long id){
		return new ResponseEntity<>(service.postCheckout(id), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/carts/{id}/products/{productId}")
	public ResponseEntity<DeletedDTO> deleteProduct(@PathVariable long id, @PathVariable long productId){
		return new ResponseEntity<>(service.deleteById(id, productId), HttpStatus.OK);
	}
}
