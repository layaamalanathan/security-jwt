package code.logic.tamil.securityjwt.repository;

import code.logic.tamil.securityjwt.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository <Role,Long>{
    Optional<Role> findByName(String name);
}
