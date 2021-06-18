package uces.edu.ar.shoppingCart.security;

import java.io.Serializable;

public class JwtRequest implements Serializable {
	
	private static final long serialVersionUID = 5869528523247865643L;
	
	private String username;
	private String password;
	
	public JwtRequest() {}
	
	public JwtRequest(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
