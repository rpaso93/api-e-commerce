package uces.edu.ar.shoppingCart.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import uces.edu.ar.shoppingCart.model.dto.DeletedDTO;
import uces.edu.ar.shoppingCart.model.dto.UserCreateDTO;
import uces.edu.ar.shoppingCart.model.dto.UserDTO;
import uces.edu.ar.shoppingCart.model.dto.UserRecoveryDTO;
import uces.edu.ar.shoppingCart.model.dto.UserUpdateDTO;

@Service
public interface UserService{
	List<UserDTO> getAll();
	UserDTO save(UserCreateDTO user);
	UserDTO update(Optional<Integer> id, UserUpdateDTO userDto);
	UserDTO getById(Integer id);
	DeletedDTO delete(Optional<Integer> id);
	UserRecoveryDTO sendEmail(Integer id);
	HttpStatus processKey(UUID key);
}
