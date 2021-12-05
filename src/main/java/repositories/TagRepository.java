package repositories;

import entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Boolean existsTagByName(String name);
    Tag findByName(String name);
}
