package uces.edu.ar.shoppingCart.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import uces.edu.ar.shoppingCart.exception.ProductErrorType;
import uces.edu.ar.shoppingCart.exception.ProductException;

import uces.edu.ar.shoppingCart.model.Product;
import uces.edu.ar.shoppingCart.model.dto.ProductDTO;
import uces.edu.ar.shoppingCart.model.dto.DeletedDTO;

import uces.edu.ar.shoppingCart.repository.ProductsRepository;
import uces.edu.ar.shoppingCart.service.ProductsService;

@Service
public class ProductsServiceImpl implements ProductsService{
	
	private final ProductsRepository productRepo;
		
	public ProductsServiceImpl(ProductsRepository productRepo) {
		super();
		this.productRepo = productRepo;
	}

	@Override
	public List<ProductDTO> getAll() {
		
		List<Product> products = productRepo.findAll();
		List<ProductDTO> productsDTO = new ArrayList<>(products.size());
		
		for(Product product: products) {
			ProductDTO productDTO = new ProductDTO();
			BeanUtils.copyProperties(product, productDTO);
			productsDTO.add(productDTO);
		}
		
		return productsDTO;
	}

	@Override
	public ProductDTO getById(Long id) {
		
		Optional<Product> product = productRepo.findById(id);
		ProductDTO productDTO = new ProductDTO();
		
		if(product.isPresent()) {
			BeanUtils.copyProperties(product.get(), productDTO);
		}else {
			throw new ProductException(ProductErrorType.PRODUCT_NOT_PRESENT);
		}
		
		return productDTO;
	}

	@Override
	public ProductDTO post(ProductDTO productDTO) {
		if(postIsValid(productDTO)) {
			Product newProduct = new Product();	
			BeanUtils.copyProperties(productDTO, newProduct);
			
			newProduct = productRepo.save(newProduct);
			BeanUtils.copyProperties(newProduct, productDTO);
		}
		
		return productDTO;
	}

	@Override
	public ProductDTO put(Optional<Long> id, ProductDTO productDTO) {
		if(!id.isPresent()) {
			throw new ProductException(ProductErrorType.PRODUCT_ID_REQUIRED);
		}else {
			Optional<Product> productInDb = productRepo.findById(id.get());
			
			if(productInDb.isPresent()) {
				Product updProduct = new Product();
				checkAtributes(productDTO);
				productDTO.setId(id.get());
				BeanUtils.copyProperties(productDTO, updProduct);
				updProduct = productRepo.save(updProduct);
				BeanUtils.copyProperties(updProduct, productDTO);
			}else {
				throw new ProductException(ProductErrorType.PRODUCT_NOT_PRESENT);
			}
		}				
		return productDTO;
	}

	@Override
	public DeletedDTO deleteById(Optional<Long> id) {
		DeletedDTO borrado;
		if(id.isPresent()) {
			Optional<Product> productInDb = productRepo.findById(id.get());
			
			if(productInDb.isPresent())
			{
				productRepo.deleteById(id.get());
				borrado = new DeletedDTO(id.get());
			}else {
				throw new ProductException(ProductErrorType.PRODUCT_NOT_PRESENT);
			}
		}else {
			throw new ProductException(ProductErrorType.PRODUCT_NOT_PRESENT);
		}
			
		return borrado;
	}
	
	private boolean postIsValid(ProductDTO productDTO) {
		
		if(productDTO.getId() != null) {
			throw new ProductException(ProductErrorType.PRODUCT_ID_NOT_REQUIRED);
		}
		checkAtributes(productDTO);
		return true;
	}
	
	private void checkAtributes(ProductDTO productDTO) {
		if(StringUtils.isEmpty(productDTO.getDescription())) {
			throw new ProductException(ProductErrorType.PRODUCT_DESCRIPTION_REQUIRED);
		}
		if(productDTO.getStock() != null) {
			if(productDTO.getStock() <= 0) {
				throw new ProductException(ProductErrorType.PRODUCT_STOCK_INVALID);
			}
		}else { 
			throw new ProductException(ProductErrorType.PRODUCT_STOCK_REQUIRED);
		}
		if(productDTO.getUnitPrice() != null) {
			if(productDTO.getUnitPrice().doubleValue() <= 0.00){
				throw new ProductException(ProductErrorType.PRODUCT_UNITPRICE_INVALID);
			}
		}else {
			throw new ProductException(ProductErrorType.PRODUCT_UNITPRICE_REQUIRED);
		}
	}
	
}
