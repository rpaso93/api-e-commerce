package uces.edu.ar.shoppingCart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import uces.edu.ar.shoppingCart.security.JwtRequest;
import uces.edu.ar.shoppingCart.security.JwtResponse;
import uces.edu.ar.shoppingCart.security.JwtTokenUtil;
import uces.edu.ar.shoppingCart.service.impl.JwtServiceImpl;

@RestController
@CrossOrigin
public class JwtAuthController {
	
	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private JwtTokenUtil jwtToken;
	@Autowired
	private JwtServiceImpl jwtService;
	
	@PostMapping(value="/authenticate")
	public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) throws Exception {
		
		authenticate(authRequest.getUsername(),authRequest.getPassword());
		
		final UserDetails userDetails = jwtService.loadUserByUsername(authRequest.getUsername());
		final String token = jwtToken.generateToken(userDetails);
		
		return ResponseEntity.ok(new JwtResponse(token));
	}
	
	
	private void authenticate(String username, String password) throws Exception {
		jwtService.checkUser(username, password);
		try {
			authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch(DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch(BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
