package uces.edu.ar.shoppingCart.exception;

public enum BatchErrorType {
	CARTS_TO_PROCESS_NOT_FOUND(404,"Carts to be processed not present in repository."),
	DATE_PARAM_INVALID(400,"The following date param is invalid: "),
	DATE_FROM_INVALID(400, "From must be lower than to"),
	DATE_TO_INVALID(400, "To must be greater than from"),
	DATE_FROM_IS_AFTER_TODAY(400, "From can't be greater than today"), 
	PROCESSED_CARTS_NOT_FOUND(404, "Processed carts not present in repository.");
	
	private int code;
	private String message;
	
	BatchErrorType(int code, String message){
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
