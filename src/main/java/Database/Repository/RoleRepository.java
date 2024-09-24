package Database.Repository;

import Database.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    boolean existsByName(String name);

}
