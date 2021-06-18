package uces.edu.ar.shoppingCart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import uces.edu.ar.shoppingCart.controller.CartController;
import uces.edu.ar.shoppingCart.model.dto.ErrorAPI;

@ControllerAdvice(assignableTypes = {CartController.class})
public class CartServiceErrorAdvice {
	
	@ExceptionHandler({RuntimeException.class})
    public ResponseEntity<String> handleRunTimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
	
	@ExceptionHandler({CartException.class})
	public ResponseEntity<ErrorAPI> handleCartException(CartException e){
		return ResponseError.error(e.getCode(),e.getError(),e.getMessage());
	}
	
	@ExceptionHandler({ProductException.class})
	public ResponseEntity<ErrorAPI> handleProductException(ProductException e){
		return ResponseError.error(e.getCode(),e.getError(),e.getMessage());
	}
}
