package phuc.devops.tech.restaurant.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phuc.devops.tech.restaurant.Entity.Invoice;
import phuc.devops.tech.restaurant.Entity.User;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {
}
