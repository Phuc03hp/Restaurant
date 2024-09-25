package phuc.devops.tech.restaurant.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phuc.devops.tech.restaurant.Entity.DiningTable;
import phuc.devops.tech.restaurant.Entity.User;

public interface DiningTableRepository extends JpaRepository<DiningTable, Long> {
}
