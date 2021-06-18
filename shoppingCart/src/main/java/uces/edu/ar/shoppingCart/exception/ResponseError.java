package uces.edu.ar.shoppingCart.exception;

import org.springframework.http.ResponseEntity;

import uces.edu.ar.shoppingCart.model.dto.ErrorAPI;

public class ResponseError {

	public static ResponseEntity<ErrorAPI> error(int code, String errorCode, String message){
		return ResponseEntity.status(code).body(new ErrorAPI(errorCode,message));
	}
}
