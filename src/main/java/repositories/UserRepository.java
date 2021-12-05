package repositories;

import entities.Review;
import entities.User;
import entities.response.UserResponse;
import enums.NetworksName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUserName (String userName);
    Boolean existsUserByUserName (String userName);
    Optional<User> findBySocialNetworkIdAndSocialNetworkName (Long socialNetworkId, NetworksName networksName);
    Boolean existsBySocialNetworkIdAndSocialNetworkName (Long socialNetworkId, NetworksName networksName);

    @Query(value = "SELECT NEW entities.response.UserResponse(a.id, a.userName) FROM User a ")
    List<UserResponse> getAllUsers();
}
