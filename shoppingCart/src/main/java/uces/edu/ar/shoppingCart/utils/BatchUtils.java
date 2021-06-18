package uces.edu.ar.shoppingCart.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uces.edu.ar.shoppingCart.exception.BatchErrorType;
import uces.edu.ar.shoppingCart.exception.BatchException;
import uces.edu.ar.shoppingCart.model.Cart;
import uces.edu.ar.shoppingCart.model.Product;
import uces.edu.ar.shoppingCart.model.Report;
import uces.edu.ar.shoppingCart.model.dto.ProductDTO;
import uces.edu.ar.shoppingCart.model.dto.ReportDTO;

public class BatchUtils {

	public static ReportDTO reportToDto(Report reporte) {
		ReportDTO reportDto = new ReportDTO();
		
		reportDto.setId(reporte.getId());
		reportDto.setProcessedDateTime(reporte.getProcessedDateTime());
		reportDto.setProfit(reporte.getProfit());
		reportDto.setTotalCartsFailed(reporte.getTotalCartsFailed());
		reportDto.setTotalCartsProcessed(reporte.getTotalCartsProcessed());
		reportDto.setWithoutStockProducts(productToDto(reporte.getProducts()));
		
		return reportDto;
	}
	
	private static Set<ProductDTO> productToDto(Set<Product> products){
		Set<ProductDTO> withoutStock = new HashSet<>();
		if(products.size() > 0) {
			for(Product p : products) {
				ProductDTO pDto = new ProductDTO();
				pDto.setId(p.getId());
				pDto.setDescription(p.getDescription());
				pDto.setStock(p.getStock());
				pDto.setUnitPrice(p.getUnitPrice());
				withoutStock.add(pDto);
			}
		}		
		return withoutStock;
	}
	
	private static LocalDateTime testParse(String date, String name) {
		LocalDateTime formatedDate = null;
		if(date != null) {
			try {
				formatedDate = LocalDateTime.parse(date);
			}catch(DateTimeParseException e) {
				throw new BatchException(BatchErrorType.DATE_PARAM_INVALID, name);
			}
		}
		return formatedDate;
	}
	
	public static void testDates(String from, String to) {
		LocalDateTime _from = testParse(from, "from");
		LocalDateTime _to = testParse(to, "to");
		if(_from != null && _to != null) {
			checkFromAndToAreValids(_from, _to);
		}
	}

	private static void checkFromAndToAreValids(LocalDateTime _from, LocalDateTime _to) {
		if(_from.isAfter(_to)) {
			throw new BatchException(BatchErrorType.DATE_FROM_INVALID);
		}
		if(_to.isBefore(_from)) {
			throw new BatchException(BatchErrorType.DATE_TO_INVALID);
		}
	}

	public static void testFrom(String from) {
		LocalDateTime _from = testParse(from, "from");
		LocalDateTime today = LocalDateTime.now();
		if(_from.isAfter(today)) {
			throw new BatchException(BatchErrorType.DATE_FROM_IS_AFTER_TODAY);
		}
	}

	public static void testTo(String to) {
		testParse(to, "to");		
	}

	public static void checkCartsLenght(List<Cart> processedCarts) {
		if(processedCarts.size() == 0) {
			throw new BatchException(BatchErrorType.PROCESSED_CARTS_NOT_FOUND);
		}		
	}

	public static void checkReadyCarts(List<Cart> readyCarts) {
		if(readyCarts.size() == 0) {
			throw new BatchException(BatchErrorType.CARTS_TO_PROCESS_NOT_FOUND);
		}		
	}
}
