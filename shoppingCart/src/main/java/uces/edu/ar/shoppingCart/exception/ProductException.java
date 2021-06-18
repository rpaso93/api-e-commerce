package uces.edu.ar.shoppingCart.exception;

public class ProductException extends RuntimeException {
	
	private static final long serialVersionUID = -713346832130596742L;
	
	private int code;
	private String error;
	
	public ProductException(ProductErrorType error){
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
