package uces.edu.ar.shoppingCart.exception;

public class BatchException extends RuntimeException {

	private static final long serialVersionUID = 8895543036936693854L;
	
	private int code;
	private String error;
	
	public BatchException(BatchErrorType error){
		super(error.getMessage());
		this.setError(error.name());
		this.setCode(error.getCode());
	}
	
	public BatchException(BatchErrorType error, String name){
		super(error.getMessage() + name);
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
