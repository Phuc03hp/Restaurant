package phuc.devops.tech.restaurant.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phuc.devops.tech.restaurant.Entity.Customer;
import phuc.devops.tech.restaurant.Entity.User;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
}
