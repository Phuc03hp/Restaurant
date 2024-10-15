package phuc.devops.tech.restaurant.Repository;

import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phuc.devops.tech.restaurant.Entity.Category;
import phuc.devops.tech.restaurant.Entity.Order;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String > {
}
