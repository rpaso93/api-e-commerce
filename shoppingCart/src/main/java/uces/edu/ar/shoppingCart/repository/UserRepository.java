package uces.edu.ar.shoppingCart.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uces.edu.ar.shoppingCart.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	Optional<User> findByUsername(String username);
	Optional<User> findByKey(UUID key);
	Optional<User> findByIdAndIsActive(Integer id, boolean isActive);
	List<User> findAllByIsActive(boolean b);
}
