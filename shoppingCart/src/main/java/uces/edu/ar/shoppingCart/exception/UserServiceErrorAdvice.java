package uces.edu.ar.shoppingCart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import uces.edu.ar.shoppingCart.controller.JwtAuthController;
import uces.edu.ar.shoppingCart.controller.UserController;
import uces.edu.ar.shoppingCart.model.dto.ErrorAPI;

@ControllerAdvice(assignableTypes = {UserController.class, JwtAuthController.class})
public class UserServiceErrorAdvice {
	
	@ExceptionHandler({RuntimeException.class})
    public ResponseEntity<String> handleRunTimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
	
	@ExceptionHandler({UserException.class})
	public ResponseEntity<ErrorAPI> handleProductException(UserException e){
		return ResponseError.error(e.getCode(),e.getError(),e.getMessage());
	}
}

