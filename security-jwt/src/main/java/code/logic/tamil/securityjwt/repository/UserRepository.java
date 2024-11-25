package code.logic.tamil.securityjwt.repository;

import code.logic.tamil.securityjwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository {
    Optional<User> findByUsername(String username);
}
