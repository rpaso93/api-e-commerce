package uces.edu.ar.shoppingCart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import uces.edu.ar.shoppingCart.model.dto.CartDTO;
import uces.edu.ar.shoppingCart.model.dto.ReportDTO;

@Service
public interface BatchService {

	List<CartDTO> getProcessedCarts();
	
	List<CartDTO> getProcessedCartsFrom(String from);
	
	List<CartDTO> getProcessedCartsTo(String to);
	
	List<CartDTO> getProcessedCarts(String from, String to);

	ReportDTO processCarts();		
}
