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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import uces.edu.ar.shoppingCart.model.dto.DeletedDTO;
import uces.edu.ar.shoppingCart.model.dto.ProductDTO;
import uces.edu.ar.shoppingCart.service.ProductsService;

@RestController
@Validated
public class ProductsController {
	
	@Autowired
	private ProductsService service;
	
	@GetMapping(value = "/products")
	public ResponseEntity<List<ProductDTO>> getProducts(){
		return new ResponseEntity<>(service.getAll(),HttpStatus.OK);
	}
	
	@GetMapping(value = "/products/{id}")
	public ResponseEntity<ProductDTO> getProducts(@PathVariable long id){
		return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
	}
	
	@PostMapping(path = "/products")
	public ResponseEntity<ProductDTO> postProduct(@Valid @RequestBody ProductDTO product){
		return new ResponseEntity<>(service.post(product), HttpStatus.CREATED);
	}
	
	@PutMapping(value = {"/products/{id}","/products"})
	public ResponseEntity<ProductDTO> putProduct(@PathVariable(required=false) Optional<Long> id, @Valid @RequestBody ProductDTO product){
		return new ResponseEntity<>(service.put(id, product), HttpStatus.OK);
	}
	
	@DeleteMapping(value = {"/products/{id}","/products"})
	public ResponseEntity<DeletedDTO> deleteProduct(@PathVariable(required=false) Optional<Long> id){
		return new ResponseEntity<>(service.deleteById(id), HttpStatus.OK);
	}
}
