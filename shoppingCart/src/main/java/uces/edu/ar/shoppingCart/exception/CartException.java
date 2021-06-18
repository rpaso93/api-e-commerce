package uces.edu.ar.shoppingCart.exception;

public class CartException extends RuntimeException {
	private static final long serialVersionUID = 3556389550908089359L;

	private int code;
	private String error;
	
	public CartException(CartErrorType error){
		super(error.getMessage());
		this.setError(error.name());
		this.setCode(error.getCode());
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
