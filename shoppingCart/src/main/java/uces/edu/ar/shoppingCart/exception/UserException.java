package uces.edu.ar.shoppingCart.exception;

public class UserException extends RuntimeException {

	private static final long serialVersionUID = 850399573322424787L;
	
	private int code;
	private String error;
	
	public UserException(UserErrorType error){
		super(error.getMessage());
		this.setError(error.name());
		this.setCode(error.getCode());
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
