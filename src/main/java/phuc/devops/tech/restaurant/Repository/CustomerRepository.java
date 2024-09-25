package phuc.devops.tech.restaurant.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phuc.devops.tech.restaurant.Entity.Customer;
import phuc.devops.tech.restaurant.Entity.User;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
