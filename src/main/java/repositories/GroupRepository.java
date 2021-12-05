package repositories;

import entities.Group;
import entities.Review;
import entities.response.ReviewResponse;
import enums.ReviewGroups;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Boolean existsByGroups(ReviewGroups name);
    Group findByGroups(ReviewGroups name);
}