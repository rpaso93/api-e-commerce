package uces.edu.ar.shoppingCart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import uces.edu.ar.shoppingCart.controller.BatchController;
import uces.edu.ar.shoppingCart.model.dto.ErrorAPI;

@ControllerAdvice(assignableTypes = {BatchController.class})
public class BatchServiceErrorAdvice {
	
	@ExceptionHandler({RuntimeException.class})
    public ResponseEntity<String> handleRunTimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
	
	@ExceptionHandler({BatchException.class})
	public ResponseEntity<ErrorAPI> handleCartException(BatchException e){
		return ResponseError.error(e.getCode(),e.getError(),e.getMessage());
	}
}
