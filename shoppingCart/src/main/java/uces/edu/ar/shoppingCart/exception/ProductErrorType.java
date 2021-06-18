package uces.edu.ar.shoppingCart.exception;

public enum ProductErrorType {
	PRODUCT_NOT_PRESENT(404,"Product not present in repository."),
	PRODUCT_ID_REQUIRED(400,"Product id is required."),
	PRODUCT_ID_NOT_REQUIRED(400,"Product id is not required."),
	PRODUCT_DESCRIPTION_REQUIRED(400,"Product description is required."),
	PRODUCT_STOCK_REQUIRED(400,"Product stock is required."),
	PRODUCT_STOCK_INVALID(400,"Product stock must be greater than 0."),
	PRODUCT_UNITPRICE_REQUIRED(400,"Product unitprice is required."),
	PRODUCT_UNITPRICE_INVALID(400,"Product unitprice must be greater than 0.");
	
	private int code;
	private String message;
	
	ProductErrorType(int code, String message){
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
