package uces.edu.ar.shoppingCart.exception;

public enum CartErrorType {
	PRODUCT_PRESENT_IN_CART(409,"Product is present in carts."),
	PRODUCT_NOT_PRESENT_IN_CART(404,"Product is not present in cart."),
	CART_NOT_PRESENT(404,"Cart not present in repository."),
	CART_FULLNAME_REQUIRED(400, "Cart fullName is required."),
	CART_EMAIL_REQUIRED(400, "Cart email is required."),
	PRODUCT_QUANTITY_REQUIRED(400,"Product quantity is required."),
	PRODUCT_QUANTITY_INVALID(400,"Product quantity must be greater than 0."),
	CART_PROCESSING_NOT_ALLOW_DELETION(409,"Cart processing. Deletion is not possible."),
	CART_STATUS_NOT_ALLOW_CHECKOUT(409,"Cart status not allow checkout."),
	CART_STATUS_NOT_SUPPORTED(400,"Cart status not supported"),
	PRODUCT_STOCK_INSUFFICIENT(409,"Product stock insufficient");
	
	private int code;
	private String message;
	
	CartErrorType(int code, String message){
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