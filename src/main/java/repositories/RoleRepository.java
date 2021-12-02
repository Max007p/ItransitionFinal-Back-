package repositories;

import entities.Review;
import entities.User;
import entities.UserRole;
import enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<UserRole, Integer> {
    Optional<UserRole> findByRole(Role role);
}
