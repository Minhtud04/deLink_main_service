package depauw.mnguyen.delink.user;

import depauw.mnguyen.delink.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
// The second generic type MUST match the @Id field type in the User entity.
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * @param email The email address to search for.
     * @return An Optional containing the User if found, otherwise an empty Optional.
     */
    Optional<User> findByEmail(String email);

}