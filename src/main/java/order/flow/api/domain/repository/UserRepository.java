package order.flow.api.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import order.flow.api.domain.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    
    UserDetails findByEmail(String email);

}
