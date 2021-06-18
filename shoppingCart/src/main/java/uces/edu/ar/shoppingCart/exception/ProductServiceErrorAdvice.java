package uces.edu.ar.shoppingCart.exception;

import uces.edu.ar.shoppingCart.controller.ProductsController;
import uces.edu.ar.shoppingCart.exception.ProductException;
import uces.edu.ar.shoppingCart.model.dto.ErrorAPI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice(assignableTypes = {ProductsController.class})
public class ProductServiceErrorAdvice {
	
	@ExceptionHandler({RuntimeException.class})
    public ResponseEntity<String> handleRunTimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
		
	@ExceptionHandler({ProductException.class})
	public ResponseEntity<ErrorAPI> handleProductException(ProductException e){
		return ResponseError.error(e.getCode(),e.getError(),e.getMessage());
	}
}
