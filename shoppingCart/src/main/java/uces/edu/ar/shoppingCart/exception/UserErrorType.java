package uces.edu.ar.shoppingCart.exception;

public enum UserErrorType {
	USER_NOT_PRESENT(404,"User not present in repository."),
	USER_USERNAME_REQUIRED(400,"Username is required."),
	USER_PASSWORD_REQUIRED(400,"Password is required."),
	USER_USERNAME_ALREADY_EXISTS(409,"User username already exist."),
	USER_USERNAME_INVALID(400,"User username must be a valid email"),
	USER_PASSWORD_INVALID(400,"User password must be a valid: Minimum length 8, Maximum length 32. At least 1 char, At least 1 number. Not allowed special characters, only (-_.)"),
	USER_RECOVERY_INVALID(400,"Invalid data for user recovery"),
	USER_INVALID_CREDENTIALS(400, "Invalid credentials"),
	USER_IS_BLOCKED(409,"User blocked");
	
	private int code;
	private String message;
	
	UserErrorType(int code, String message){
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
