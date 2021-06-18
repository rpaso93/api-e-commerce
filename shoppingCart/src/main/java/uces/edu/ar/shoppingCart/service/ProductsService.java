package uces.edu.ar.shoppingCart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import uces.edu.ar.shoppingCart.model.dto.DeletedDTO;
import uces.edu.ar.shoppingCart.model.dto.ProductDTO;

@Service
public interface ProductsService {
	
	List<ProductDTO> getAll();
	ProductDTO getById(Long id);
	ProductDTO post(ProductDTO product);
	ProductDTO put(Optional<Long> id, ProductDTO product);
	DeletedDTO deleteById(Optional<Long> id);
}
