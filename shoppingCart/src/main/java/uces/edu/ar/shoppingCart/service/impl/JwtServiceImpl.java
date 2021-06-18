package uces.edu.ar.shoppingCart.service.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.util.StringUtils;
import uces.edu.ar.shoppingCart.exception.UserErrorType;
import uces.edu.ar.shoppingCart.exception.UserException;
import uces.edu.ar.shoppingCart.model.User;
import uces.edu.ar.shoppingCart.repository.UserRepository;
import uces.edu.ar.shoppingCart.service.JwtService;

@Service
public class JwtServiceImpl implements JwtService {
	
	@Autowired
	private UserRepository userRepo;	
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepo.findByUsername(username);
		if (!user.isPresent()) {
			throw new UserException(UserErrorType.USER_NOT_PRESENT);
		}
		return new org.springframework.security.core.userdetails.User(user.get().getUsername(), 
				user.get().getPassword(), new ArrayList<>());
	}

	public void checkUser(String username, String password) {
		checkUserAndPassword(username, password);
		Optional<User> user = userRepo.findByUsername(username);
		isPresent(user);
		isBlocked(user.get());
		passwordMatches(password, user.get());
	}

	private void passwordMatches(String password, User user) {
		if(!encoder.matches(password, user.getPassword())) {
			if(user.getTries() < 3) {
				int updatedTries = user.getTries() + 1;
				user.setTries(updatedTries);
				userRepo.save(user);
			}else {
				user.setBlocked(true);
				userRepo.save(user);
				throw new UserException(UserErrorType.USER_IS_BLOCKED);
			}			
			throw new UserException(UserErrorType.USER_INVALID_CREDENTIALS);
		}
	}

	private void isPresent(Optional<User> user) {
		if (!user.isPresent()) {
			throw new UserException(UserErrorType.USER_INVALID_CREDENTIALS);
		}
	}
	
	private void isBlocked(User user) {
		if(user.isBlocked()) {
			throw new UserException(UserErrorType.USER_IS_BLOCKED);
		}
	}	

	private void checkUserAndPassword(String username, String password) {
		if(StringUtils.isEmpty(username)) {
			throw new UserException(UserErrorType.USER_USERNAME_REQUIRED);
		}
		if(StringUtils.isEmpty(password)) {
			throw new UserException(UserErrorType.USER_PASSWORD_REQUIRED);
		}
	}	
	
}
