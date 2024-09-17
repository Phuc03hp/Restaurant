package phuc.devops.tech.shoeshop.Repository;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phuc.devops.tech.shoeshop.Entity.Model;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {
}
