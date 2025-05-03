package phuc.devops.tech.restaurant.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phuc.devops.tech.restaurant.Entity.Payment;

public interface PaymentRespository extends JpaRepository<Payment, String> {

    
}
