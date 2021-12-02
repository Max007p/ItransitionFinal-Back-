package repositories;

import entities.User;
import enums.NetworksName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByUserName (String userName);
    Boolean existsUserByUserName (String userName);

    Optional<User> findBySocialNetworkIdAndSocialNetworkName (Long socialNetworkId, NetworksName networksName);
    Boolean existsBySocialNetworkIdAndSocialNetworkName (Long socialNetworkId, NetworksName networksName);
}
