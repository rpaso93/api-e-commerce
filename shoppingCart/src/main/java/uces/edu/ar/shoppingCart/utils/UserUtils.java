package uces.edu.ar.shoppingCart.utils;

import java.util.Optional;

import io.micrometer.core.instrument.util.StringUtils;
import uces.edu.ar.shoppingCart.exception.UserErrorType;
import uces.edu.ar.shoppingCart.exception.UserException;
import uces.edu.ar.shoppingCart.model.User;
import uces.edu.ar.shoppingCart.model.dto.UserCreateDTO;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserUtils {
	public static void isUserPresent(Optional<User> user) {
		if(!user.isPresent()) {
			throw new UserException(UserErrorType.USER_NOT_PRESENT);
		}
	}
	
	public static void checkUserAttr(UserCreateDTO userDto) {
		if(StringUtils.isEmpty(userDto.getUsername())) {
			throw new UserException(UserErrorType.USER_USERNAME_REQUIRED);
		}else {
			validEmail(userDto.getUsername());
		}
		checkPassword(userDto.getPassword());
	}
	
	public static void checkPassword(String password) {
		if(StringUtils.isEmpty(password)) {
			throw new UserException(UserErrorType.USER_PASSWORD_REQUIRED);
		}
		if(password.length() > 8 && password.length() < 32) {
			if(!contains(password)) {
				throw new UserException(UserErrorType.USER_PASSWORD_INVALID);				
			}
		}else {
			throw new UserException(UserErrorType.USER_PASSWORD_INVALID);
		}
	}
	
	private static boolean contains(String password) {
		String regex = "^(?=.*[0-9])(?=.*[a-z])([a-z0-9_\\-\\.]+)$"; 
		return useRegex(regex, password);
	}
	
	private static void validEmail(String username) {
		String regex = "^[-a-z0-9~!$%^&*_=+}{\\'?]+(\\.[-a-z0-9~!$%^&*_=+}{\\'?]+)*@([a-z0-9_][-a-z0-9_]*(\\.[-a-z0-9_]+)*\\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,5})?$";
		
		if(!useRegex(regex, username)) {
			throw new UserException(UserErrorType.USER_USERNAME_INVALID);
		}
	}
	
	private static boolean useRegex(String regex, String toValidate) {
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(toValidate);
		return matcher.find();
	}
}
