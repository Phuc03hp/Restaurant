package phuc.devops.tech.shoeshop.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phuc.devops.tech.shoeshop.Entity.Model;
import phuc.devops.tech.shoeshop.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
