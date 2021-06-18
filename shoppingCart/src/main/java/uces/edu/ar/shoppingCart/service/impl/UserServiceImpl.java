package uces.edu.ar.shoppingCart.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import uces.edu.ar.shoppingCart.exception.UserErrorType;
import uces.edu.ar.shoppingCart.exception.UserException;
import uces.edu.ar.shoppingCart.model.User;
import uces.edu.ar.shoppingCart.model.dto.DeletedDTO;
import uces.edu.ar.shoppingCart.model.dto.UserCreateDTO;
import uces.edu.ar.shoppingCart.model.dto.UserDTO;
import uces.edu.ar.shoppingCart.model.dto.UserRecoveryDTO;
import uces.edu.ar.shoppingCart.model.dto.UserUpdateDTO;
import uces.edu.ar.shoppingCart.repository.UserRepository;
import uces.edu.ar.shoppingCart.service.EmailService;
import uces.edu.ar.shoppingCart.service.UserService;
import uces.edu.ar.shoppingCart.utils.UserUtils;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired EmailService emailService;
	
	public UserDTO save(UserCreateDTO createUserDto) {

		UserUtils.checkUserAttr(createUserDto);
		checkIfUserExists(createUserDto);
		
		User newUser = new User();
				
		newUser.setUsername(createUserDto.getUsername());
		newUser.setPassword(encoder.encode(createUserDto.getPassword()));
		
		newUser = userRepo.save(newUser);
		UserDTO userDto = new UserDTO();
		BeanUtils.copyProperties(newUser, userDto);
		
		return userDto;
	}

	@Override
	public List<UserDTO> getAll() {
		List<UserDTO> usersDTO_list = new ArrayList<UserDTO>();
		List<User> users_list = userRepo.findAllByIsActive(true);
		
		for(User user: users_list) {
			UserDTO userDto = new UserDTO();
			BeanUtils.copyProperties(user, userDto);
			usersDTO_list.add(userDto);
		}

		return usersDTO_list;
	}

	@Override
	public UserDTO update(Optional<Integer> id, UserUpdateDTO passwordDto) {
		
		Optional<User> user = userRepo.findById(id.get());
		UserUtils.isUserPresent(user);
		UserUtils.checkPassword(passwordDto.getPassword());
		String encodedPassword = encoder.encode(passwordDto.getPassword());
		user.get().setPassword(encodedPassword);
		User updated = userRepo.save(user.get());
		UserDTO userDto = new UserDTO();
		BeanUtils.copyProperties(updated, userDto);
		
		return userDto;
	}

	

	@Override
	public UserDTO getById(Integer id) {
		Optional<User> user = userRepo.findById(id);
		UserUtils.isUserPresent(user);
		UserDTO userDto = new UserDTO();
		BeanUtils.copyProperties(user.get(), userDto);
		
		return userDto;
	}

	@Override
	public DeletedDTO delete(Optional<Integer> id) {

		Optional<User> user = userRepo.findByIdAndIsActive(id.get(), true);
		UserUtils.isUserPresent(user);
		user.get().setActive(false);
		userRepo.save(user.get());
		DeletedDTO deleted = new DeletedDTO();
		deleted.setId(id.get());
		return deleted;
	}
	
	private void checkIfUserExists(UserCreateDTO userDto) {
		Optional<User> existingUser = userRepo.findByUsername(userDto.getUsername());
		if(existingUser.isPresent()) {
			throw new UserException(UserErrorType.USER_USERNAME_ALREADY_EXISTS);
		}
	}

	@Override
	public UserRecoveryDTO sendEmail(Integer id) {
		Optional<User> user = userRepo.findByIdAndIsActive(id, true);
		UserUtils.isUserPresent(user);
		
		UUID key = UUID.randomUUID();
		user.get().setKey(key);
		
		String subject = user.get().isBlocked() ? "Recuperación de usuario bloqueado" : "Recuperación de password";
		String message = user.get().isBlocked() ? "Para recuperar tu usuario haz click en" : "Para cambiar tu contrasela haz click en";
		message += " http://localhost:8080/users/recovery/process" + key;
		
		UserRecoveryDTO recover = new UserRecoveryDTO();
		recover.setKey(key);
		recover.setEmail(user.get().getUsername());
		recover.setSubject(subject);
		recover.setMessage(message);
		
		userRepo.save(user.get());
		return recover;
	}
	

	
	@Override
	public HttpStatus processKey(UUID key) {
		Optional<User> user = userRepo.findByKey(key);
		if(user.isPresent()) {
			user.get().setKey(null);
			user.get().setBlocked(false);
			user.get().setTries(0);
			userRepo.save(user.get());
			return HttpStatus.OK;
		}else {
			throw new UserException(UserErrorType.USER_RECOVERY_INVALID);
		}
	}
}
