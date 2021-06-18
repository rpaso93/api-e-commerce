package uces.edu.ar.shoppingCart.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import uces.edu.ar.shoppingCart.model.dto.DeletedDTO;
import uces.edu.ar.shoppingCart.model.dto.UserCreateDTO;
import uces.edu.ar.shoppingCart.model.dto.UserDTO;
import uces.edu.ar.shoppingCart.model.dto.UserRecoveryDTO;
import uces.edu.ar.shoppingCart.model.dto.UserUpdateDTO;
import uces.edu.ar.shoppingCart.service.UserService;

@RestController
@Validated
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping(value="/users")
	public ResponseEntity<List<UserDTO>> getUsers(){
		return new ResponseEntity<>(userService.getAll(),HttpStatus.OK);
	}
	
	@GetMapping(value="/users/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable(required=true) Integer id){
		return new ResponseEntity<>(userService.getById(id),HttpStatus.OK);
	}
	
	@PostMapping(value="/users/create")
	public ResponseEntity<UserDTO> createUser(@RequestBody UserCreateDTO user) {
		return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
	}
	
	@PutMapping(value= {"/users/{id}", "/users"})
	public ResponseEntity<UserDTO> updateUser(@PathVariable(required=false) Optional<Integer> id, @RequestBody UserUpdateDTO user) {
		return new ResponseEntity<>(userService.update(id, user), HttpStatus.OK);
	}
	
	@DeleteMapping(value = {"/users/{id}", "/users"})
	public ResponseEntity<DeletedDTO> deleteUser(@PathVariable(required=false) Optional<Integer> id){
		return new ResponseEntity<>(userService.delete(id), HttpStatus.OK);
	}
	
	@GetMapping(value= "/users/recovery/send/{id}")
	public ResponseEntity<UserRecoveryDTO> sendUserKey(@PathVariable Integer id){
		return new ResponseEntity<>(userService.sendEmail(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/users/recovery/process/{key}")
	public HttpStatus processUserKey(@PathVariable UUID key){
		return userService.processKey(key);
	}
}
